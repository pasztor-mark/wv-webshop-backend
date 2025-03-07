package com.example.webshopbackend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "shop_users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    @Column(unique = true, name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String username;

    @Column(name = "admin")
    @Builder.Default
    private Boolean admin = false;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Item> items;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItems> cartItems;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
