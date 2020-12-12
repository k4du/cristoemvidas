package br.com.cristoemvidas.config;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordCodec {

    public static String encode(String str) {
        Integer workFactor = 12;
        return BCrypt.hashpw(str,BCrypt.gensalt(workFactor));
	}

    public static Boolean checkPass(String candidate, String hashed) {
        return BCrypt.checkpw(candidate,hashed);
	}
}
