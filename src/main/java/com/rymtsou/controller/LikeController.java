package com.rymtsou.controller;

import com.rymtsou.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<?> likePost(@PathVariable Long postId) {
        Boolean liked = likeService.likePost(postId);
        return ResponseEntity.ok(liked ? "Liked post" : "Unliked post");
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<?> likeComment(@PathVariable Long commentId) {
        Boolean liked = likeService.likeComment(commentId);
        return ResponseEntity.ok(liked ? "Liked comment" : "Unliked comment");
    }
}
