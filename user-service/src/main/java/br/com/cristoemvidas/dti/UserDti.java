package br.com.cristoemvidas.dti;

import br.com.cristoemvidas.model.User;
import br.com.cristoemvidas.util.PasswordCodec;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;

@Data
public class UserDti {

    private String id;

    @NotBlank(message = "Nome não pode ser vazia")
    @NotNull(message = "Nome não pode ser vazia")
    private String name;
    @NotBlank(message = "Login não pode ser vazia")
    @NotNull(message = "Login não pode ser vazia")
    private String username;

    @Size(min = 8, message = "Senha tem que ter no mínimo 8 dígitos")
    private String password;

    @Email(message = "Email não pode ser vazio e precisa ter o formato xxxx@xxxx.xxxx")
    private String email;

    @NotNull(message = "Ativo não pode ser vazio")
    private Boolean active;

    @NotEmpty
    private List<String> roles;
    
    
    public void fillUser(User user){

        user.setActive(this.getActive());
        user.setUsername(this.getUsername());
        user.setEmail(this.getEmail());
        user.setName(this.getName());
        if(Objects.nonNull(this.getPassword())){
            user.setPassword(PasswordCodec.encode(this.getPassword()));
        }

    }

}
