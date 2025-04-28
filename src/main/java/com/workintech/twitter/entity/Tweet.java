package com.workintech.twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tweet", schema = "twitter")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotBlank(message = "Tweet içeriği boş olamaz.")
    @Size(max = 280, message = "Tweet içeriği en fazla 280 karakter olabilir.")
    @Column(name = "content", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Retweet> retweets;
}
