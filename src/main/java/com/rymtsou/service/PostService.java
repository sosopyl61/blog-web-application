package com.rymtsou.service;

import com.rymtsou.model.request.CreatePostRequestDto;
import com.rymtsou.model.request.UpdatePostRequestDto;
import com.rymtsou.model.response.CreatePostResponseDto;
import com.rymtsou.model.response.GetPostResponseDto;
import com.rymtsou.model.response.GetPostsResponseDto;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<CreatePostResponseDto> createPost(CreatePostRequestDto requestDto);
    Optional<GetPostResponseDto> getPostById(Long id);
    List<GetPostsResponseDto> getPostsByUsername(String username);
    List<GetPostsResponseDto> getAllPosts();
    Optional<GetPostResponseDto> updatePost(UpdatePostRequestDto requestDto);
    Boolean deletePost(Long id);
    Long getLikesCountById(Long id);
}
