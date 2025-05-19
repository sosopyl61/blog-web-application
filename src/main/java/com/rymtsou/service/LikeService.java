package com.rymtsou.service;

public interface LikeService {
    Boolean likePost(Long postId);
    Boolean likeComment(Long commentId);
}
