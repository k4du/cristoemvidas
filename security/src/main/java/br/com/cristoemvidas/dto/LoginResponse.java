package br.com.cristoemvidas.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class LoginResponse {


    public String tokenPrefix;
    public String token;
    public String name;
    public String id;
    public Date expirationTime;

}
