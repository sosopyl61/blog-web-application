package com.rymtsou.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "posts")
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @SequenceGenerator(name = "post_seq_gen", sequenceName = "posts_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "post_seq_gen")
    private Long id;
    @Column(nullable = false)
    private String title;
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp created;

    @LastModifiedDate
    private Timestamp updated;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

}
