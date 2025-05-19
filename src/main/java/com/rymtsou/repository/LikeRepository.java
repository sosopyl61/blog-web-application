package com.rymtsou.repository;

import com.rymtsou.model.domain.Comment;
import com.rymtsou.model.domain.Like;
import com.rymtsou.model.domain.Post;
import com.rymtsou.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    Optional<Like> findByUserAndComment(User user, Comment comment);
    Long countLikesByPost(Post post);
    Long countLikesByComment(Comment comment);
}
