package com.rymtsou.repository;

import com.rymtsou.model.domain.Comment;
import com.rymtsou.model.domain.Like;
import com.rymtsou.model.domain.Post;
import com.rymtsou.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    Optional<Like> findByUserAndComment(User user, Comment comment);
    Long countLikesByPost(Post post);
    Long countLikesByComment(Comment comment);
}
