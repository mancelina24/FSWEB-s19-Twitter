package com.workintech.twitter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "role", schema = "twitter")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "authority")
    private String authority;

    @Override
    public String getAuthority()
    {
        return this.authority;
    }

}
