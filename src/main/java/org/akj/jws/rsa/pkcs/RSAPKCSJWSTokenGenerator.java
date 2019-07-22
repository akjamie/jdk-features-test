package org.akj.jws.rsa.pkcs;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;

import java.util.Map;

/**
 * JWS(Json Web Signature) token encoded with RSA(PKCS)
 */
@AllArgsConstructor
public class RSAPKCSJWSTokenGenerator {
    private JWSAlgorithm algorithm;
    private RSAKey rsaKey;

    public String generate(Map<String, Object> payload) throws JOSEException {

        // Create RSA-signer with the private key
        JWSSigner signer = new RSASSASigner(rsaKey);
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(payload);

        // Prepare JWS object with simple string as payload
        JWSObject jwsObject = new JWSObject(
                new JWSHeader.Builder(algorithm).keyID(rsaKey.getKeyID()).build(),
                new Payload(jsonObject));

        // Compute the RSA signature
        jwsObject.sign(signer);

        // generate token
        String s = jwsObject.serialize();

        return s;
    }
}
