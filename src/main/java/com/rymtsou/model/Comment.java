package com.rymtsou.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

@Scope("prototype")
@Entity(name = "comments")
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @SequenceGenerator(name = "comment_seq_gen", sequenceName = "comments_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "comment_seq_gen")
    private Long id;
    @Column(name = "comment_text")
    private String commentText;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp created;

    @LastModifiedDate
    private Timestamp updated;

    @ManyToOne
    @JoinColumn(name = "comment_author_id", nullable = false)
    private User comm_author;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
