package com.investment_qna;

import java.net.URL;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

@Service
public class GoogleAuthService {

    private static final String CLIENT_ID =
        "69093629046-ceodbhe966eshu26kl2eu7snpb4386qh.apps.googleusercontent.com";

    public JWTClaimsSet verifyToken(String token) throws Exception {

        JWKSource<SecurityContext> keySource =
            new RemoteJWKSet<>(new URL("https://www.googleapis.com/oauth2/v3/certs"));

        ConfigurableJWTProcessor<SecurityContext> jwtProcessor =
            new DefaultJWTProcessor<>();

        JWSKeySelector<SecurityContext> keySelector =
            new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, keySource);

        jwtProcessor.setJWSKeySelector(keySelector);

        JWTClaimsSet claims = jwtProcessor.process(token, null);

        // Validate audience (VERY IMPORTANT)
        if (!claims.getAudience().contains(CLIENT_ID)) {
            throw new RuntimeException("Invalid Google token audience");
        }

        return claims;
    }
}
