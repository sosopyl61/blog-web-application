package com.rymtsou.model.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.List;

@Entity(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"posts", "comments"})
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq_gen")
    private Long id;
    @Column(unique = true)
    private String username;
    private String firstname;
    @Column(name = "second_name")
    private String secondName;
    private String email;
    private Integer age;
    private String sex;

    @CreatedDate
    @Column(name = "created", updatable = false)
    private Timestamp created;

    @LastModifiedDate
    @Column(name = "updated")
    private Timestamp updated;

    @JsonManagedReference
    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    @JsonManagedReference
    @OneToMany(mappedBy = "comm_author")
    private List<Comment> comments;
}
