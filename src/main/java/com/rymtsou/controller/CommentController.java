package com.rymtsou.controller;

import com.rymtsou.model.request.CreateCommentRequestDto;
import com.rymtsou.model.request.UpdateCommentRequestDto;
import com.rymtsou.model.response.CreateCommentResponseDto;
import com.rymtsou.model.response.GetCommentResponseDto;
import com.rymtsou.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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

@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Create post",
            description = "Creating comment to the post by post's id.")
    @ApiResponses(value = {
            @ApiResponse(description = "Comment was created.", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = CreateCommentResponseDto.class))),
            @ApiResponse(description = "Comment creating was failed.", responseCode = "409", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CreateCommentResponseDto> createComment(@RequestBody @Valid CreateCommentRequestDto dto) {
        Optional<CreateCommentResponseDto> createdComment = commentService.createComment(dto);
        if (createdComment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(createdComment.get(), HttpStatus.CREATED);
    }

    @Operation(summary = "Get comment by id",
            description = "Getting user's comment by comment's id.")
    @ApiResponses(value = {
            @ApiResponse(description = "Comment was received.", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetCommentResponseDto.class))),
            @ApiResponse(description = "Comment was not found.", responseCode = "404", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetCommentResponseDto> getCommentById(@PathVariable @Parameter(description = "Comment's id") Long id) {
        Optional<GetCommentResponseDto> comment = commentService.getCommentById(id);
        if (comment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comment.get(), HttpStatus.OK);
    }

    @Operation(summary = "Get all comments by post id",
            description = "Getting all comments of the post by post's id.")
    @ApiResponses(value = {
            @ApiResponse(description = "Comments were received.", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetCommentResponseDto.class)))
    })
    @GetMapping("/inPost/{id}")
    public ResponseEntity<List<GetCommentResponseDto>> getAllCommentsByPostId(@PathVariable @Parameter(description = "Post's id") Long id) {
        List<GetCommentResponseDto> comments = commentService.getAllCommentsByPostId(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @Operation(summary = "Edit comment",
            description = "Editing user's comment by comment's id.")
    @ApiResponses(value = {
            @ApiResponse(description = "Comment was updated.", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetCommentResponseDto.class))),
            @ApiResponse(description = "Comment updating was failed.", responseCode = "409", content = @Content)
    })
    @PutMapping()
    public ResponseEntity<GetCommentResponseDto> updateComment(@RequestBody @Valid UpdateCommentRequestDto dto) {
        Optional<GetCommentResponseDto> updatedComment = commentService.updateComment(dto);
        if (updatedComment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(updatedComment.get(), HttpStatus.OK);
    }

    @Operation(summary = "Delete comment",
            description = "Deleting user's comment by comment's id.")
    @ApiResponses(value = {
            @ApiResponse(description = "Comment was deleted.", responseCode = "204", content = @Content),
            @ApiResponse(description = "Comment deleting was failed.", responseCode = "409", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable @Parameter(description = "Comment's id") Long id) {
        Boolean result = commentService.deleteComment(id);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Count likes of the comment",
            description = "Getting amount of likes of the comment by comment's id.")
    @ApiResponses(value = {
            @ApiResponse(description = "Likes were received.", responseCode = "200"),
    })
    @GetMapping("/countLikes/{id}")
    public ResponseEntity<String> countLikes(@PathVariable @Parameter(description = "Comment's id") Long id) {
        Long likes = commentService.getLikesCountById(id);
        return new ResponseEntity<>("Likes: " + likes, HttpStatus.OK);
    }
}
