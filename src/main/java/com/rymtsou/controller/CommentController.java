package com.rymtsou.controller;

import com.rymtsou.model.request.CreateCommentRequestDto;
import com.rymtsou.model.request.DeleteByIdRequestDto;
import com.rymtsou.model.request.UpdateCommentRequestDto;
import com.rymtsou.model.response.CreateCommentResponseDto;
import com.rymtsou.model.response.GetCommentResponseDto;
import com.rymtsou.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CreateCommentResponseDto> createComment(@RequestBody CreateCommentRequestDto dto) {
        Optional<CreateCommentResponseDto> createdComment = commentService.createComment(dto);
        if (createdComment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(createdComment.get(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCommentResponseDto> getCommentById(@PathVariable Long id) {
        Optional<GetCommentResponseDto> comment = commentService.getCommentById(id);
        if (comment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comment.get(), HttpStatus.OK);
    }

    @GetMapping("/inPost/{id}")
    public ResponseEntity<List<GetCommentResponseDto>> getAllCommentsByPostId(@PathVariable Long id) {
        List<GetCommentResponseDto> comments = commentService.getAllCommentsByPostId(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<GetCommentResponseDto> updateComment(@RequestBody UpdateCommentRequestDto dto) {
        Optional<GetCommentResponseDto> updatedComment = commentService.updateComment(dto);
        if (updatedComment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(updatedComment.get(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deletePost(@RequestBody DeleteByIdRequestDto dto) {
        Boolean result = commentService.deleteComment(dto);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
