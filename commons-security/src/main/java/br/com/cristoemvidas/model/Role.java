package br.com.cristoemvidas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Role implements GrantedAuthority {

    @JsonIgnore
    @Override
    public String getAuthority() {
        return  "ROLE_".concat(this.role.toUpperCase());
    }

    String role;
}
