package org.akj.jws.hmac;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.*;

class HMACJWSTokenGeneratorTest {
    private HMACJWSTokenGenerator generator;
    private Map<String, Object> payload = new HashMap<String, Object>();
    private JSONObject jsonObject = null;
    private Map<String, String> sharedSecrets = new HashMap<String, String>();

    @BeforeEach
    void setUp() {
        payload.put("guid", UUID.randomUUID().toString());
        payload.put("kid", "TECH-SIGNING-PRIVATE-KEY");
        Date date = new Date();
        payload.put("exp", date.getTime() + 10 * 60 * 1000);

        // verify token
        jsonObject = new JSONObject();
        jsonObject.putAll(payload);

        // Generate random bits to to as shared secret
        sharedSecrets.put("256", gernateSharedSecret(256));
        sharedSecrets.put("384", gernateSharedSecret(384));
        sharedSecrets.put("512", gernateSharedSecret(512));
    }

    private String gernateSharedSecret(int bit) {
        // Generate random 256-bit (32-byte) shared secret
        SecureRandom random = new SecureRandom();
        byte[] sharedSecret = new byte[bit / 8];
        random.nextBytes(sharedSecret);

        return Base64.getEncoder().encodeToString(sharedSecret);
    }

    @Test
    void generateWithHMC256Test() throws JOSEException, ParseException {
        System.out.println("--------JWT(HS256)--------");
        generator = new HMACJWSTokenGenerator(JWSAlgorithm.HS256, sharedSecrets.get("256"));
        // generate token
        String token = generator.generate(payload);
        System.out.println(token);

        JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(jsonObject));
        jwsObject = JWSObject.parse(token);

        JWSVerifier verifier = new MACVerifier(sharedSecrets.get("256"));

        Assertions.assertTrue(jwsObject.verify(verifier), "invalid token");
    }

    @Test
    void generateWithHMC384Test() throws JOSEException, ParseException {
        System.out.println("--------JWT(HS384)--------");
        generator = new HMACJWSTokenGenerator(JWSAlgorithm.HS384, sharedSecrets.get("384"));
        // generate token
        String token = generator.generate(payload);
        System.out.println(token);
        JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS384), new Payload(jsonObject));
        jwsObject = JWSObject.parse(token);

        JWSVerifier verifier = new MACVerifier(sharedSecrets.get("384"));

        Assertions.assertTrue(jwsObject.verify(verifier), "invalid token");
    }

    @Test
    void generateWithHMC512Test() throws JOSEException, ParseException {
        System.out.println("--------JWT(HS512)--------");
        generator = new HMACJWSTokenGenerator(JWSAlgorithm.HS512, sharedSecrets.get("512"));
        // generate token
        String token = generator.generate(payload);
        System.out.println(token);
        JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS512), new Payload(jsonObject));
        jwsObject = JWSObject.parse(token);

        JWSVerifier verifier = new MACVerifier(sharedSecrets.get("512"));

        Assertions.assertTrue(jwsObject.verify(verifier), "invalid token");
    }

    @Test
    public void verifyToken() throws ParseException, JOSEException {
        generator = new HMACJWSTokenGenerator(JWSAlgorithm.HS512, sharedSecrets.get("512"));
        // generate token
        String token = generator.generate(payload);
        System.out.println(token);
        JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS512), new Payload(jsonObject));
        jwsObject = JWSObject.parse(token);

        System.out.println("header:" + jwsObject.getHeader().toString());
        System.out.println("payload:" + jwsObject.getPayload().toString());
        System.out.println("signature:" + jwsObject.getSignature().toString());

    }
}