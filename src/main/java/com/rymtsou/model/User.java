package com.rymtsou.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Scope("prototype")
@Entity(name = "users")
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq_gen")
    private Long id;

    private String firstname;
    @Column(name = "second_name")
    private String secondName;
    private String email;
    private Integer age;
    private String sex;

    @JsonIgnore
    @CreatedDate
    @Column(name = "created", updatable = false)
    private Timestamp created;

    @JsonIgnore
    @LastModifiedDate
    @Column(name = "updated")
    private Timestamp updated;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "comm_author")
    private List<Comment> comments;
}
