package br.com.cristoemvidas.service;

import br.com.cristoemvidas.model.UserToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityHelper {

    public static UserToken userData(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.nonNull(authentication) && authentication instanceof UsernamePasswordAuthenticationToken){
            return (UserToken) authentication.getPrincipal();
        }
        return null;
    }
}
