package br.com.cristoemvidas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

@CompoundIndex(name = "username", def = "{'username': 1}", background = true)
@CompoundIndex(name = "username_active", def = "{'username': 1, 'active': 1}", background = true)
public class User {

    @Id
    private String id;
    private String name;
    private String username;
    private String password;
    private String email;
    private Boolean active;

    private List<Role> roles;

}
