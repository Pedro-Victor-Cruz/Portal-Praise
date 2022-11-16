package br.com.praise.portal.model;

import br.com.praise.portal.Enum.Permissions;
import br.com.praise.portal.Enum.RolesEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Document(collection = "users_portal")
public class User implements UserDetails {
    
    @Id
    @JsonProperty("_id")
    private String ID;
    private String firstname;
    private String lastname;
    private String key;
    private String email;
    private String password;
    private Set<Permission> perms;

    public User(String ID, String firstname, String lastname, String key, String email, String password, Set<Permission> perms) {
        this.ID = ID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.key = key;
        this.email = email;
        this.password = password;
        this.perms = perms;
    }

    public User(String firstname, String lastname, String key, String email, String password, Set<Permission> perms) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.key = key;
        this.email = email;
        this.password = password;
        this.perms = perms;
    }

    public User() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perms;
    }

    @Override
    public String getUsername() {
        return email;
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

    public void addPermissions(List<Permissions> perms) {
        Set<Permission> permissions = new HashSet<>();
        perms.forEach(permissions1 -> permissions.add(new Permission(permissions1)));
        this.setPerms(permissions);
    }

    public boolean hasPermission(String perm) {
        for(Permission permission : this.perms)
            if (permission.getPerm().name().equals(perm) || permission.getPerm() == Permissions.ADMIN)
                return true;
        return false;
    }

    public boolean getPermission(String perm) {
        for(Permission permission : this.perms)
            if (permission.getPerm().name().equals(perm))
                return true;
        return false;
    }
}

