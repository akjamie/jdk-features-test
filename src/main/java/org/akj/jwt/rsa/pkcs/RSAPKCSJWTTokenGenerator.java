package org.akj.jwt.rsa.pkcs;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.Map;

/**
 * JWS(Json Web Signature) token encoded with RSA(PKCS)
 */
@AllArgsConstructor
public class RSAPKCSJWTTokenGenerator {
    private JWSAlgorithm algorithm;
    private RSAKey rsaKey;

    public String generate(String subject, String issuer, Map<String, Object> payload) throws JOSEException {
        // Create RSA-signer with the private key
        JWSSigner signer = new RSASSASigner(rsaKey);

        // Prepare JWT with claims set
        JWTClaimsSet.Builder claimBuilder = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer(issuer)
                .expirationTime(new Date(new Date().getTime() + 60 * 1000));

        // add custom claims
        payload.forEach((k, v) -> claimBuilder.claim(k, v));
        JWTClaimsSet claimsSet = claimBuilder.build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKey.getKeyID()).build(), claimsSet);

        // Apply the HMAC protection
        signedJWT.sign(signer);
        String s = signedJWT.serialize();
        return s;
    }
}
