package br.com.praise.portal.model;

import br.com.praise.portal.Enum.Roles;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class Role implements GrantedAuthority {

    @Id
    @JsonProperty("_id")
    private String id;
    private Roles role;

    @Override
    public String getAuthority() {
        return this.role.name();
    }

}
