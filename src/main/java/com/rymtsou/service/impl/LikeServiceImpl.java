package com.rymtsou.service.impl;

import com.rymtsou.exception.EntityNotFoundException;
import com.rymtsou.model.domain.Comment;
import com.rymtsou.model.domain.Like;
import com.rymtsou.model.domain.Post;
import com.rymtsou.model.domain.Security;
import com.rymtsou.model.domain.User;
import com.rymtsou.repository.CommentRepository;
import com.rymtsou.repository.LikeRepository;
import com.rymtsou.repository.PostRepository;
import com.rymtsou.repository.UserRepository;
import com.rymtsou.service.LikeService;
import com.rymtsou.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {


    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AuthUtil authUtil;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, AuthUtil authUtil) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.authUtil = authUtil;
    }

    @Override
    public Boolean likePost(Long postId) {
        Security currentSecurity = authUtil.getCurrentSecurity();

        User user = userRepository.findById(currentSecurity.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + currentSecurity.getUserId()));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return false;
        }

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        likeRepository.save(like);
        return true;
    }

    @Override
    public Boolean likeComment(Long commentId) {
        Security currentSecurity = authUtil.getCurrentSecurity();

        User user = userRepository.findById(currentSecurity.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + currentSecurity.getUserId()));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

        Optional<Like> existingLike = likeRepository.findByUserAndComment(user, comment);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return false;
        }

        Like like = Like.builder()
                .user(user)
                .comment(comment)
                .build();

        likeRepository.save(like);
        return true;
    }
}
