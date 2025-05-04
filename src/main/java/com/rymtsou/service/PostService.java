package com.rymtsou.service;

import com.rymtsou.model.request.CreatePostRequestDto;
import com.rymtsou.model.request.DeleteByIdRequestDto;
import com.rymtsou.model.request.FindPostRequestDto;
import com.rymtsou.model.request.UpdatePostRequestDto;
import com.rymtsou.model.response.CreatePostResponseDto;
import com.rymtsou.model.response.GetPostResponseDto;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<CreatePostResponseDto> createPost(CreatePostRequestDto requestDto);
    Optional<GetPostResponseDto> getPostById(Long id);
    List<GetPostResponseDto> getPostsByUsername(FindPostRequestDto requestDto);
    List<GetPostResponseDto> getAllPosts();
    Optional<GetPostResponseDto> updatePost(UpdatePostRequestDto requestDto);
    Boolean deletePost(DeleteByIdRequestDto requestDto);
}
