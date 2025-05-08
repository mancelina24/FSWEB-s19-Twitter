package com.workintech.twitter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "role", schema = "twitter")
public class Role implements GrantedAuthority  {

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

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj==null || obj.getClass() != this.getClass())
            return false;
        Role role=(Role) obj;
        return  role.getId().equals(this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
}
