package com.workintech.twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="user", schema = "twitter")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotBlank(message = "Kullanıcı adı boş olamaz.")
    @Size(min = 3, max = 50, message = "Kullanıcı adı 3 ile 50 karakter arasında olmalıdır.")
    @Column(name="user_name", unique = true)
    private String username;

    @NotBlank(message = "Şifre boş olamaz.")
   // @Size(max = 20, message = "Şifre max 20 karakter olmalıdır.")
    @Column(name="password")
    private String password;

    @NotNull
    @NotBlank
    @Email(message = "Geçerli bir e-posta adresi giriniz.")
    @Size(max = 50, message = "Şifre max 50 karakter olmalıdır.")
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            schema = "Twitter",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return email;  // Spring Security giriş için
    }

    @Override
    public String getPassword() {
        return password;
    }

    // kullanıcı adı gibi davranacak başka bir getter
    public String getUserNameField() {
        return username; // DTO için kullanılabilir
    }


    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj==null || obj.getClass() != this.getClass())
            return false;
        User user=(User) obj;
        // Güvenli null kontrolü
        return this.id != null && this.id.equals(user.getId());

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
}
