package br.com.cristoemvidas.config;

import br.com.cristoemvidas.model.User;
import br.com.cristoemvidas.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MongoAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findFirstByUsernameAndActive(username, true);

        if(Objects.nonNull(user) && PasswordCodec.checkPass(password, user.getPassword())){

            try{
                return new UsernamePasswordAuthenticationToken(mapper.writeValueAsString(user), password, user.getRoles());
            }catch (Exception ex){
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
