package com.rymtsou.service.impl;

import com.rymtsou.exception.EntityNotFoundException;
import com.rymtsou.model.domain.Comment;
import com.rymtsou.model.domain.Post;
import com.rymtsou.model.domain.Role;
import com.rymtsou.model.domain.Security;
import com.rymtsou.model.domain.User;
import com.rymtsou.model.request.CreateCommentRequestDto;
import com.rymtsou.model.request.DeleteByIdRequestDto;
import com.rymtsou.model.request.UpdateCommentRequestDto;
import com.rymtsou.model.response.CreateCommentResponseDto;
import com.rymtsou.model.response.GetCommentResponseDto;
import com.rymtsou.repository.CommentRepository;
import com.rymtsou.repository.LikeRepository;
import com.rymtsou.repository.PostRepository;
import com.rymtsou.repository.UserRepository;
import com.rymtsou.service.CommentService;
import com.rymtsou.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthUtil authUtil;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, AuthUtil authUtil, PostRepository postRepository, LikeRepository likeRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.authUtil = authUtil;
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<CreateCommentResponseDto> createComment(CreateCommentRequestDto requestDto) {
        Security optionalSecurity = authUtil.getCurrentSecurity();

        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + requestDto.getPostId()));

        User user;

        if (optionalSecurity.getRole().equals(Role.ADMIN)) {
            if (requestDto.getUsername() == null) {
                throw new IllegalArgumentException("Admin must provide a username to create comment!");
            }
            user = userRepository.findByUsername(requestDto.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + requestDto.getUsername()));
        } else {
            if (requestDto.getUsername() != null) {
                throw new AccessDeniedException("You cannot specify username.");
            }
            user = userRepository.findById(optionalSecurity.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + optionalSecurity.getUserId()));
        }

        Comment comment = Comment.builder()
                .commentText(requestDto.getCommentText())
                .post(post)
                .comm_author(user)
                .build();

        Comment createdComment = commentRepository.save(comment);
        return Optional.of(CreateCommentResponseDto.builder()
                .commentText(createdComment.getCommentText())
                .author(createdComment.getComm_author().getUsername())
                .build());
    }

    @Override
    public Optional<GetCommentResponseDto> getCommentById(Long id) {
        return commentRepository.findById(id)
                .map(comment -> GetCommentResponseDto.builder()
                        .commentText(comment.getCommentText())
                        .author(comment.getComm_author().getUsername())
                        .created(comment.getCreated().toLocalDateTime())
                        .updated(comment.getUpdated().toLocalDateTime())
                        .build());
    }

    @Override
    public List<GetCommentResponseDto> getAllCommentsByPostId(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new EntityNotFoundException("Post not found with id: " + postId);
        }

        return commentRepository.findAllCommentsByPostId(postId)
                .stream()
                .map(comment -> GetCommentResponseDto.builder()
                        .commentText(comment.getCommentText())
                        .author(comment.getComm_author().getUsername())
                        .created(comment.getCreated().toLocalDateTime())
                        .updated(comment.getUpdated().toLocalDateTime())
                        .build())
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<GetCommentResponseDto> updateComment(UpdateCommentRequestDto requestDto) {
        if (!authUtil.canAccessComment(requestDto.getId())) {
            throw new AccessDeniedException("You do not have permission to update this comment.");
        }

        Comment comment = commentRepository.findById(requestDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + requestDto.getId()));

        if (requestDto.getCommentText() != null && !requestDto.getCommentText().equals(comment.getCommentText()))
            comment.setCommentText(requestDto.getCommentText());

        Comment updatedComment = commentRepository.save(comment);
        commentRepository.flush();

        return Optional.of(GetCommentResponseDto.builder()
                .commentText(updatedComment.getCommentText())
                .author(comment.getComm_author().getUsername())
                .created(comment.getCreated().toLocalDateTime())
                .updated(comment.getUpdated().toLocalDateTime())
                .build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteComment(DeleteByIdRequestDto dto) {
        if (authUtil.canAccessComment(dto.getId())) {
            commentRepository.deleteById(dto.getId());
            return !commentRepository.existsById(dto.getId());
        }
        throw new AccessDeniedException("Access denied, id: " + dto.getId());
    }

    @Override
    public Long getLikesCountById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + id));
        return likeRepository.countLikesByComment(comment);
    }
}
