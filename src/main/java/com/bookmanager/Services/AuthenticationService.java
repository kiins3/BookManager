package com.bookmanager.Services;

import com.bookmanager.Config.JwtConfig;
import com.bookmanager.DTOs.Request.AuthenticationRequest;
import com.bookmanager.DTOs.Request.IntrospectRequest;
import com.bookmanager.DTOs.Response.AuthenticationResponse;
import com.bookmanager.DTOs.Response.IntrospectResponse;
import com.bookmanager.Exception.ErrorCode;
import com.bookmanager.Exception.RException;
import com.bookmanager.Models.User;
import com.bookmanager.Repositories.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.nimbusds.jwt.JWTClaimsSet;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtConfig jwtConfig;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RException(ErrorCode.USER_NOT_FOUND));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new RException(ErrorCode.LOGIN_FAILED);
        }

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(User user) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .claim("role", user.getRole())
                .claim("email", user.getEmail())
                .claim("status", user.getStatus())
                .issuer("aaa.com")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1,ChronoUnit.HOURS)))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(jwtConfig.getSecretKey().getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RException(ErrorCode.INVALID_TOKEN);
        }
    }
}
