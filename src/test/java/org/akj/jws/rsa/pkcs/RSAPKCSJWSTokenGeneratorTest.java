package org.akj.jws.rsa.pkcs;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.*;

class RSAPKCSJWSTokenGeneratorTest {
    private RSAPKCSJWSTokenGenerator generator;
    private RSAKey rsaJWK;
    private RSAKey rsaPublicJWK;
    private Map<String, Object> payload = new HashMap<String, Object>();
    private JSONObject jsonObject = null;

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
        Date date = new Date();
        payload.put("exp", date.getTime() + 10 * 60 * 1000);

        // verify token
        jsonObject = new JSONObject();
        jsonObject.putAll(payload);

        System.out.println("RSA private key:" + rsaJWK.getPrivateExponent().toString());
        System.out.println("RSA public key:" + rsaJWK.getPublicExponent().toString());
        System.out.println("RSA public key:" + Base64.getEncoder().encodeToString(rsaJWK.toPublicKey().getEncoded()));
    }

    @Test
    void generateWithRSA256() throws JOSEException, ParseException {
        System.out.println("--------JWT(RS256)--------");
        generator = new RSAPKCSJWSTokenGenerator(JWSAlgorithm.RS256, rsaJWK);
        String token = generator.generate(payload);
        System.out.println("token:" + token);

        JWSObject jwsObject = new JWSObject(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJWK.getKeyID()).build(),
                new Payload(jsonObject));
        jwsObject = JWSObject.parse(token);
        JWSVerifier verifier = new RSASSAVerifier(rsaPublicJWK);

        Assertions.assertTrue(jwsObject.verify(verifier));
        Assertions.assertEquals(jwsObject.getPayload().toJSONObject(), jsonObject);
    }

    @Test
    void generateWithRSA384() throws JOSEException, ParseException {
        System.out.println("--------JWT(RS256)--------");
        generator = new RSAPKCSJWSTokenGenerator(JWSAlgorithm.RS384, rsaJWK);
        String token = generator.generate(payload);
        System.out.println("token:" + token);

        JWSObject jwsObject = new JWSObject(
                new JWSHeader.Builder(JWSAlgorithm.RS384).keyID(rsaJWK.getKeyID()).build(),
                new Payload(jsonObject));
        jwsObject = JWSObject.parse(token);
        JWSVerifier verifier = new RSASSAVerifier(rsaPublicJWK);

        Assertions.assertTrue(jwsObject.verify(verifier));
        Assertions.assertEquals(jwsObject.getPayload().toJSONObject(), jsonObject);
    }


    @Test
    void generateWithRSA512() throws JOSEException, ParseException {
        System.out.println("--------JWT(RS256)--------");
        generator = new RSAPKCSJWSTokenGenerator(JWSAlgorithm.RS512, rsaJWK);
        String token = generator.generate(payload);
        System.out.println("token:" + token);

        JWSObject jwsObject = new JWSObject(
                new JWSHeader.Builder(JWSAlgorithm.RS512).keyID(rsaJWK.getKeyID()).build(),
                new Payload(jsonObject));
        jwsObject = JWSObject.parse(token);
        JWSVerifier verifier = new RSASSAVerifier(rsaPublicJWK);

        Assertions.assertTrue(jwsObject.verify(verifier));
        Assertions.assertEquals(jwsObject.getPayload().toJSONObject(), jsonObject);
    }
}