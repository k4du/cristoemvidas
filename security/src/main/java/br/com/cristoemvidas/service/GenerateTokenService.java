package br.com.cristoemvidas.service;

import br.com.cristoemvidas.dto.LoginResponse;
import br.com.cristoemvidas.dto.UserToken;
import br.com.cristoemvidas.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.Date;

@Service
public class GenerateTokenService {

    // EXPIRATION_TIME = 10 dias
    static final long EXPIRATION_TIME = 860_000_000;


    private static PrivateKey privateKey;

    static final String TOKEN_PREFIX = "Bearer";

    private static ObjectMapper mapper = new ObjectMapper();


    @Autowired
    @Qualifier("privateKeySecurity")
    private void setPrivateKey(PrivateKey privateKey) {
        GenerateTokenService.privateKey = privateKey;
    }


    public static void addAuthentication(HttpServletResponse response, String userData) throws IOException {

        Date expirationTime = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        UserToken userToken = generateDataToken(userData);

        System.out.println("userToken = " + userToken);

        String JWT = Jwts.builder()
                .setSubject(mapper.writeValueAsString(userToken))
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();


        LoginResponse loginResponse = LoginResponse.builder()
                .expirationTime(expirationTime)
//                .name(user.getName())
                .tokenPrefix(TOKEN_PREFIX)
                .token(JWT)
                .build();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(loginResponse));
        response.getWriter().flush();
        response.getWriter().close();

    }

    private static UserToken generateDataToken(String userData){

        User user = mapper.convertValue(JSON.parse(userData), User.class);

        return UserToken.builder()
                .id(user.getId())
                .roles(user.getRoles()).build();
    }


}
