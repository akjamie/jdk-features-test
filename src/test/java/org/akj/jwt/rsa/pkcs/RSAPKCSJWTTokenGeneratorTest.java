package org.akj.jwt.rsa.pkcs;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class RSAPKCSJWTTokenGeneratorTest {
    private RSAPKCSJWTTokenGenerator generator;
    private RSAKey rsaJWK;
    private RSAKey rsaPublicJWK;
    private Map<String, Object> payload = new HashMap<String, Object>();

    @BeforeEach
    void setUp() throws JOSEException {
        // RSA signatures require a public and private RSA key pair,
        // the public key must be made known to the JWS recipient to
        // allow the signatures to be verified
        rsaJWK = new RSAKeyGenerator(2048)
                .keyID("123")
                .generate();
        rsaPublicJWK = rsaJWK.toPublicJWK();
        payload.put("guid", UUID.randomUUID().toString());
        payload.put("kid", "TECH-SIGNING-PRIVATE-KEY");

        System.out.println("RSA private key:" + rsaJWK.getPrivateExponent().toString());
        System.out.println("RSA public key:" + rsaJWK.getPublicExponent().toString());
        System.out.println("RSA public key:" + Base64.getEncoder().encodeToString(rsaJWK.toPublicKey().getEncoded()));
    }

    @Test
    void generateWithRSA256() throws JOSEException, ParseException {
        System.out.println("--------JWT(RS256)--------");
        generator = new RSAPKCSJWTTokenGenerator(JWSAlgorithm.RS256, rsaJWK);
        String token = generator.generate("Jamie", "https://www.ai-future.cn", payload);
        System.out.println("token:" + token);

        // On the consumer side, parse the JWS and verify its HMAC
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new RSASSAVerifier(rsaPublicJWK);

        Assertions.assertTrue(signedJWT.verify(verifier));
        Assertions.assertTrue(signedJWT.getJWTClaimsSet().getClaims().containsKey("guid"));
    }
}