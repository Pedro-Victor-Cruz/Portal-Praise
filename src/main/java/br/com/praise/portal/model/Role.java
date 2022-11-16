package br.com.praise.portal.model;

import br.com.praise.portal.Enum.RolesEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class Role implements GrantedAuthority {

    private RolesEnum role;

    public Role(RolesEnum role) {
        this.role = role;
    }

    public Role() {}

    @Override
    public String getAuthority() {
        return this.role.name();
    }

}
