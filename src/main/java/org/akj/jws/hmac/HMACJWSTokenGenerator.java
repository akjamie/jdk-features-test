package org.akj.jws.hmac;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

import java.util.Base64;
import java.util.Map;

@Slf4j
/**
 * JWS(JSON Web Signature) token encoded with Hash-based message authentication code (HMAC)
 */
public class HMACJWSTokenGenerator {
    private String sharedSecret;

    private JWSAlgorithm algorithm;

    public HMACJWSTokenGenerator(JWSAlgorithm algorithm, String sharedSecret) {
        this.algorithm = algorithm;
        this.sharedSecret = sharedSecret;
    }

    public String generate(Map<String, Object> payload) throws JOSEException {
        log.info("shared secret:" + Base64.getEncoder().encodeToString(Base64.getDecoder().decode(sharedSecret)));
        // Create HMAC signer
        JWSSigner signer = new MACSigner(sharedSecret);

        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(payload);

        // Prepare JWS object with payload
        JWSObject jwsObject = new JWSObject(new JWSHeader(this.algorithm), new Payload(jsonObject));

        // Apply the HMAC
        jwsObject.sign(signer);

        // To serialize to compact form, produces something like
        // eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
        String s = jwsObject.serialize();
        return s;
    }

}
