package com.rymtsou.util;

import com.rymtsou.exception.PostNotFoundException;
import com.rymtsou.model.domain.Post;
import com.rymtsou.model.domain.Role;
import com.rymtsou.model.domain.Security;
import com.rymtsou.repository.PostRepository;
import com.rymtsou.repository.SecurityRepository;
import com.rymtsou.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthUtil {
    private final SecurityRepository securityRepository;
    private final PostRepository postRepository;

    @Autowired
    public AuthUtil(SecurityRepository securityRepository, PostRepository postRepository) {
        this.securityRepository = securityRepository;
        this.postRepository = postRepository;
    }

    public Optional<Security> getCurrentSecurity() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return securityRepository.findByLogin(login);
    }

    public Boolean canAccessUser(Long id) {
        Optional<Security> currentSecurity = getCurrentSecurity();
        if (currentSecurity.isEmpty()) {
            throw new UsernameNotFoundException("User not found.");
        }
        return currentSecurity.get().getRole().equals(Role.ADMIN) ||
                currentSecurity.get().getUserId().equals(id);
    }

    public Boolean canAccessSecurityByLogin(String currentLogin) {
        Optional<Security> currentSecurity = getCurrentSecurity();
        if (currentSecurity.isEmpty()) {
            throw new UsernameNotFoundException("User not found.");
        }
        return currentSecurity.get().getRole().equals(Role.ADMIN) ||
                currentSecurity.get().getLogin().equals(currentLogin);
    }

    public Boolean canAccessPost(Long id) {
        Optional<Security> currentSecurity = getCurrentSecurity();
        if (currentSecurity.isEmpty()) {
            throw new UsernameNotFoundException("User not found.");
        }

        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new PostNotFoundException("Post not found with id: " + id);
        }
        return currentSecurity.get().getRole().equals(Role.ADMIN) ||
                currentSecurity.get().getUserId().equals(post.get().getAuthor().getId());
    }
}
