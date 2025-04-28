package com.workintech.twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="users", schema = "twitter")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotBlank(message = "Kullanıcı adı boş olamaz.")
    @Size(min = 3, max = 50, message = "Kullanıcı adı 3 ile 50 karakter arasında olmalıdır.")
    @Column(name="user_name", unique = true)
    private String username;

    @NotBlank(message = "Şifre boş olamaz.")
    @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır.")
    @Column(name="password")
    private String password;

    @NotNull
    @NotBlank
    @Email(message = "Geçerli bir e-posta adresi giriniz.")
    @Column(name = "email", unique = true)
    private String email;

    @CreationTimestamp
    @Column(name="registration_date", updatable = false)
    private LocalDateTime registrationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tweet> tweets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Retweet> retweets;

}
