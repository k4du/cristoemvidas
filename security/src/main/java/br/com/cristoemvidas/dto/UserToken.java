package br.com.cristoemvidas.dto;

import br.com.cristoemvidas.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserToken {

    String id;
    List<Role> roles;

}
