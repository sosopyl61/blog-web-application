package com.rymtsou.controller;

import com.rymtsou.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @Operation(summary = "Like post",
            description = "Liking post by post's id.")
    @ApiResponses(value = {
            @ApiResponse(description = "Post was liked or unliked.", content = @Content)
    })
    @PostMapping("/post/{postId}")
    public ResponseEntity<?> likePost(@PathVariable @Parameter(description = "Post's id") Long postId) {
        Boolean liked = likeService.likePost(postId);
        return ResponseEntity.ok(liked ? "Liked post" : "Unliked post");
    }

    @Operation(summary = "Like comment",
            description = "Liking comment by comment's id.")
    @ApiResponses(value = {
            @ApiResponse(description = "Comment was liked or unliked.", content = @Content)
    })
    @PostMapping("/comment/{commentId}")
    public ResponseEntity<?> likeComment(@PathVariable @Parameter(description = "Comment's id") Long commentId) {
        Boolean liked = likeService.likeComment(commentId);
        return ResponseEntity.ok(liked ? "Liked comment" : "Unliked comment");
    }
}
