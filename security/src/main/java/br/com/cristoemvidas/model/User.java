package br.com.cristoemvidas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

    @Id
    String id;
    String name;
    String username;
    String password;
    String email;

    List<Role> roles;

}
