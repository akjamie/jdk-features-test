package org.akj.jwt.hmac;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
/**
 * JWT(JSON Web Token) token encoded with Hash-based message authentication code (HMAC)
 */
public class HMACJWTTokenGenerator {
    private String sharedSecret;
    private JWSAlgorithm algorithm;

    public HMACJWTTokenGenerator(JWSAlgorithm algorithm, String sharedSecret) {
        this.algorithm = algorithm;
        this.sharedSecret = sharedSecret;
    }

    public String generate(String subject, String issuer, Map<String, Object> payload) throws JOSEException {
        log.info("shared secret:" + Base64.getEncoder().encodeToString(Base64.getDecoder().decode(sharedSecret)));
        // Create HMAC signer
        JWSSigner signer = new MACSigner(sharedSecret);

        // Prepare JWT with claims set
        JWTClaimsSet.Builder claimBuilder = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer(issuer)
                .expirationTime(new Date(new Date().getTime() + 60 * 1000));

        // add custom claims
        payload.forEach((k,v) ->claimBuilder.claim(k,v));
        JWTClaimsSet claimsSet = claimBuilder.build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(this.algorithm), claimsSet);

        // Apply the HMAC protection
        signedJWT.sign(signer);
        String s = signedJWT.serialize();

        return s;
    }

}
