package com.rymtsou.repository;

import com.rymtsou.model.domain.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {
    Boolean existsByLogin(String login);
}
