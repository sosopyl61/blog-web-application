package com.rymtsou.service.impl;

import com.rymtsou.exception.EntityNotFoundException;
import com.rymtsou.model.domain.Post;
import com.rymtsou.model.domain.Role;
import com.rymtsou.model.domain.Security;
import com.rymtsou.model.domain.User;
import com.rymtsou.model.request.CreatePostRequestDto;
import com.rymtsou.model.request.UpdatePostRequestDto;
import com.rymtsou.model.response.CreatePostResponseDto;
import com.rymtsou.model.response.GetPostResponseDto;
import com.rymtsou.model.response.GetPostsResponseDto;
import com.rymtsou.repository.LikeRepository;
import com.rymtsou.repository.PostRepository;
import com.rymtsou.repository.UserRepository;
import com.rymtsou.service.PostService;
import com.rymtsou.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthUtil authUtil;
    private final LikeRepository likeRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, AuthUtil authUtil, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.authUtil = authUtil;
        this.likeRepository = likeRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<CreatePostResponseDto> createPost(CreatePostRequestDto requestDto) {
        Security security = authUtil.getCurrentSecurity();
        User user;

        if (security.getRole().equals(Role.ADMIN)) {
            if (requestDto.getUsername() == null) {
                throw new IllegalArgumentException("Admin must provide a username to create post!");
            }
            user = userRepository.findByUsername(requestDto.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + requestDto.getUsername()));
        } else {
            if (requestDto.getUsername() != null) {
                throw new AccessDeniedException("You cannot specify username.");
            }
            user = userRepository.findById(security.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + security.getUserId()));
        }

        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .author(user)
                .build();

        Post createdPost = postRepository.save(post);

        return Optional.of(CreatePostResponseDto.builder()
                .title(createdPost.getTitle())
                .content(createdPost.getContent())
                .author(createdPost.getAuthor().getUsername())
                .build());
    }

    @Override
    public Optional<GetPostResponseDto> getPostById(Long id) {
        return postRepository.findById(id)
                .map(post -> GetPostResponseDto.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor().getUsername())
                        .created(post.getCreated().toLocalDateTime())
                        .updated(post.getUpdated().toLocalDateTime())
                        .comments(post.getComments())
                        .build());
    }

    @Override
    public List<GetPostsResponseDto> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return user.getPosts()
                .stream()
                .map(post -> GetPostsResponseDto.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor().getUsername())
                        .created(post.getCreated().toLocalDateTime())
                        .updated(post.getUpdated().toLocalDateTime())
                        .build())
                .toList();
    }

    @Override
    public List<GetPostsResponseDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(post -> GetPostsResponseDto.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor().getUsername())
                        .created(post.getCreated().toLocalDateTime())
                        .updated(post.getUpdated().toLocalDateTime())
                        .build())
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<GetPostResponseDto> updatePost(UpdatePostRequestDto requestDto) {
        if (!authUtil.canAccessPost(requestDto.getId())) {
            throw new AccessDeniedException("You do not have permission to update this post.");
        }

        Post post = postRepository.findById(requestDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + requestDto.getId()));

        if (requestDto.getTitle() != null && !requestDto.getTitle().equals(post.getTitle()))
            post.setTitle(requestDto.getTitle());
        if (requestDto.getContent() != null && requestDto.getContent().equals(post.getContent()))
            post.setContent(requestDto.getContent());

        Post updatedPost = postRepository.save(post);
        postRepository.flush();

        return Optional.of(GetPostResponseDto.builder()
                .title(updatedPost.getTitle())
                .content(updatedPost.getContent())
                .author(updatedPost.getAuthor().getUsername())
                .created(updatedPost.getCreated().toLocalDateTime())
                .updated(updatedPost.getUpdated().toLocalDateTime())
                .comments(updatedPost.getComments())
                .build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deletePost(Long id) {
        if (authUtil.canAccessPost(id)) {
            postRepository.deleteById(id);
            return !postRepository.existsById(id);
        }
        throw new AccessDeniedException("Access denied, id: " + id);
    }

    @Override
    public Long getLikesCountById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
        return likeRepository.countLikesByPost(post);
    }
}
