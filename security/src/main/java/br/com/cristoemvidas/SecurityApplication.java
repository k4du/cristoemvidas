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

	public static String SECRET_PLUB;

	@Value("${secretKey}")
	private void setSECRET(String SECRET) {
		this.SECRET = SECRET;
	}

	@Value("${secretKeyPlub}")
	private void setSECRET_PLUB(String SECRET_PLUB) {
		this.SECRET_PLUB = SECRET_PLUB;
	}

	public static void main(String[] args) {

		SpringApplication.run(SecurityApplication.class, args);
	}

	@PostConstruct
	public void createData(){

		Role role  = Role.builder()
				.role("ADMIN")
				.build();

		User user = User.builder()
				.name("Carlos Eduardo")
				.username("carlos.couto")
				.email("cadu.maia.couto@gmail.com")
				.password(PasswordCodec.encode("12345678"))
				.roles(Arrays.asList(role))
				.build();

		userRepository.save(user);

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

//	@Bean(name = "publicKeySecurity")
//	public PublicKey publicKeySecurity() throws Exception {
//
//		X509EncodedKeySpec spec =
//				new X509EncodedKeySpec(Base64.getDecoder().decode(SECRET_PLUB));
//
//		KeyFactory kf = KeyFactory.getInstance("RSA");
//		return kf.generatePublic(spec);
//	}

}
