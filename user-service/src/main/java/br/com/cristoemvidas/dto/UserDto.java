package br.com.cristoemvidas.dto;

import br.com.cristoemvidas.model.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserDto {

    private String name;
    private String email;
    private List<String> roleList;
    //TODO imagem, resumo
}
