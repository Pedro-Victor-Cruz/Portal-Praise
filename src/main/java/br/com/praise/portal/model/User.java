package br.com.praise.portal.model;

import com.google.gson.Gson;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Document(collection = "users_portal")
public class User implements UserDetails {
    
    @Id
    @JsonProperty("_id")
    private String ID;
    private String username;
    private String key;
    private String email;
    private String password;
    private Set<Role> roles;

    public User(String ID, String username, String key, String email, String password, Set<Role> roles) {
        this.ID = ID;
        this.username = username;
        this.key = key;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User(String username, String key, String email, String password, Set<Role> roles) {
        this.username = username;
        this.key = key;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

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
    public boolean isEnabled() {
        return true;
    }
}

