package br.com.cristoemvidas.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class GeneratorKeyService {

    //    TODO Para gerar a chave
    //    #!/usr/bin/env bash
    //
    //    openssl genrsa -out private_key.pem 4096
    //    openssl rsa -pubout -in private_key.pem -out public_key.pem
    //
    //    # convert private key to pkcs8 format in order to import it from Java
    //    openssl pkcs8 -topk8 -in private_key.pem -inform pem -out private_key_pkcs8.pem -outform pem -nocrypt

    public static void generatorKey() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {

        String pathPrivate = "/Users/carloscouto/desenvolvimento/estudo/private_key_pkcs8.pem";
        String privateKeyContent = new String(Files.readAllBytes(Paths.get(pathPrivate)));

        String pathPublic = "/Users/carloscouto/desenvolvimento/estudo/public_key.pem";
        String publicKeyContent = new String(Files.readAllBytes(Paths.get(pathPublic)));

        privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        publicKeyContent = publicKeyContent.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;

        System.out.println("publicKeyContent = " + publicKeyContent);
        System.out.println("privateKeyContent = " + privateKeyContent);

    }



}
