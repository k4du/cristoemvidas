package br.com.cristoemvidas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class ConfigurePublicKey {


    private static String SECRET_PLUB;

    @Value("${secretKeyPlub}")
    private void setSECRET_PLUB(String SECRET_PLUB) {
        this.SECRET_PLUB = SECRET_PLUB;
    }

    @Bean(name = "publicKeySecurity")
    public PublicKey publicKeySecurity() throws Exception {

        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(Base64.getDecoder().decode(SECRET_PLUB));

        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

}
