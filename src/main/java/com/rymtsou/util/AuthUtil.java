package com.rymtsou.util;

import com.rymtsou.exception.EntityNotFoundException;
import com.rymtsou.model.domain.Comment;
import com.rymtsou.model.domain.Post;
import com.rymtsou.model.domain.Role;
import com.rymtsou.model.domain.Security;
import com.rymtsou.repository.CommentRepository;
import com.rymtsou.repository.PostRepository;
import com.rymtsou.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
    private final SecurityRepository securityRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public AuthUtil(SecurityRepository securityRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.securityRepository = securityRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public Security getCurrentSecurity() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return securityRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Security not found with username: " + login));
    }

    public Boolean canAccessUser(Long id) {
        Security currentSecurity = getCurrentSecurity();
        return currentSecurity.getRole().equals(Role.ADMIN) ||
                currentSecurity.getUserId().equals(id);
    }

    public Boolean canAccessSecurityByLogin(String currentLogin) {
        Security currentSecurity = getCurrentSecurity();
        return currentSecurity.getRole().equals(Role.ADMIN) ||
                currentSecurity.getLogin().equals(currentLogin);
    }

    public Boolean canAccessPost(Long id) {
        Security currentSecurity = getCurrentSecurity();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));

        return currentSecurity.getRole().equals(Role.ADMIN) ||
                currentSecurity.getUserId().equals(post.getAuthor().getId());
    }

    public Boolean canAccessComment(Long id) {
        Security currentSecurity = getCurrentSecurity();
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + id));

        return currentSecurity.getRole().equals(Role.ADMIN) ||
                currentSecurity.getUserId().equals(comment.getComm_author().getId());
    }
}
