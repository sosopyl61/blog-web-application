package com.rymtsou.service;

import com.rymtsou.model.request.CreateCommentRequestDto;
import com.rymtsou.model.request.DeleteByIdRequestDto;
import com.rymtsou.model.request.UpdateCommentRequestDto;
import com.rymtsou.model.response.CreateCommentResponseDto;
import com.rymtsou.model.response.GetCommentResponseDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<CreateCommentResponseDto> createComment(CreateCommentRequestDto requestDto);
    Optional<GetCommentResponseDto> getCommentById(Long id);
    List<GetCommentResponseDto> getAllCommentsByPostId(Long postId);
    Optional<GetCommentResponseDto> updateComment(UpdateCommentRequestDto requestDto);
    Boolean deleteComment(DeleteByIdRequestDto requestDto);
}
