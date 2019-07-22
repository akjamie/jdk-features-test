package org.akj.jwt.hmac;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.*;

class HMACJWTTokenGeneratorTest {

    private HMACJWTTokenGenerator generator;
    private Map<String, Object> payload = new HashMap<String, Object>();
    private JSONObject jsonObject = null;
    private Map<String, String> sharedSecrets = new HashMap<String, String>();

    @BeforeEach
    void setUp() {
        payload.put("guid", UUID.randomUUID().toString());
        payload.put("kid", "TECH-SIGNING-PRIVATE-KEY");

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
        generator = new HMACJWTTokenGenerator(JWSAlgorithm.HS256, sharedSecrets.get("256"));
        // generate token
        String token = generator.generate("Jamie", "https://www.ai-future.org", payload);
        System.out.println(token);

        // On the consumer side, parse the JWS and verify its HMAC
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(sharedSecrets.get("256"));

        Assertions.assertTrue(signedJWT.verify(verifier), "invalid token");
        Assertions.assertTrue(new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime()));
    }
}