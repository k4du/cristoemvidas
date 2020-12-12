package br.com.cristoemvidas.service;

import br.com.cristoemvidas.model.UserToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;

@Service
public class TokenAuthenticationService {

    private static PublicKey publicKey;

    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    @Autowired
    @Qualifier("publicKeySecurity")
    private void setPublicKey(PublicKey publicKey) {
        TokenAuthenticationService.publicKey = publicKey;
    }


    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            // faz parse do token
            try{
                String user = Jwts.parser()
                        .setSigningKey(publicKey)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody()
                        .getSubject();

                ObjectMapper mapper = new ObjectMapper();
                UserToken userToken = mapper.readValue(user, UserToken.class);

                if (userToken != null) {
                    return new UsernamePasswordAuthenticationToken(userToken, null, userToken.getRoles());
                }
            }catch (SignatureException | JsonProcessingException exception){
                return null;
            }
        }
        return null;
    }
}
