package br.com.praise.portal.model;

import br.com.praise.portal.Enum.Permissions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class Permission implements GrantedAuthority {

    private Permissions perm;
    private String name;
    private String desc;

    public Permission(Permissions perm) {
        this.perm = perm;
        this.name = perm.getName();
        this.desc = perm.getDesc();
    }

    public Permission() {}

    @Override
    public String getAuthority() {
        return this.perm.name();
    }
}
