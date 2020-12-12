package br.com.cristoemvidas;

import br.com.cristoemvidas.config.PasswordCodec;
import br.com.cristoemvidas.model.Role;
import br.com.cristoemvidas.model.User;
import br.com.cristoemvidas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;


@EnableGlobalMethodSecurity(
		prePostEnabled = true,
		securedEnabled = true,
		jsr250Enabled = true)
@EnableMongoRepositories
@RestController
@SpringBootApplication
public class SecurityApplication {

	@Autowired
	UserRepository userRepository;

	public static String SECRET;

	@Value("${secretKey}")
	private void setSECRET(String SECRET) {
		this.SECRET = SECRET;
	}

	public static void main(String[] args) {

		SpringApplication.run(SecurityApplication.class, args);
	}

	@PostConstruct
	public void createData(){

		User user = userRepository.findFirstByUsername("carlos.couto");
		if(Objects.isNull(user)){
			Role role  = Role.builder()
					.role("ADMIN")
					.build();

			user = User.builder()
					.name("Carlos Eduardo")
					.username("carlos.couto")
					.email("cadu.maia.couto@gmail.com")
					.active(true)
					.password(PasswordCodec.encode("12345678"))
					.roles(Arrays.asList(role))
					.build();

			userRepository.save(user);
		}
	}

	@Bean(name = "privateKeySecurity")
	public PrivateKey privateKeySecurity() throws InvalidKeySpecException, IOException, NoSuchAlgorithmException {

		// Serviço para gerar as chaves
//		GeneratorKeyService.generatorKey();

		PKCS8EncodedKeySpec spec =
				new PKCS8EncodedKeySpec(Base64.getDecoder().decode(SECRET));
		KeyFactory kf = KeyFactory.getInstance("RSA");

		return kf.generatePrivate(spec);
	}

}
