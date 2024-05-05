package com.vitisvision.vitisvisionservice.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JwtService class is responsible for generating and validating JWT tokens.
 * It uses the io.jsonwebtoken library to generate and validate JWT tokens.
 * The class is annotated with @Service to make it a Spring-managed bean.
 */
@Service
public class JwtService {

    /**
     * The SECRET_KEY is the secret key used to sign the JWT tokens.
     */
    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;

    /**
     * The accessExpiration expiration time for the access token in milliseconds.
     */
    @Value("${application.security.jwt.access.expiration}")
    private long accessExpiration;

    /**
     * The refreshExpiration expiration time for the refresh token in milliseconds.
     */
    @Value("${application.security.jwt.refresh.expiration}")
    private long refreshExpiration;

    /**
     * The extractEmail method extracts the email from the JWT token.
     * @param jwt The JWT token from which to extract the email.
     * @return The email extracted from the JWT token.
     */
    public String extractEmail(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    /**
     * The extractExpiration method extracts the expiration date from the JWT token.
     * @param jwt The JWT token from which to extract the expiration date.
     * @return The expiration date extracted from the JWT token.
     */
    private Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    /**
     * The extractClaim method extracts a claim from the JWT token.
     * @param jwt The JWT token from which to extract the claim.
     * @param claimsResolver The function to resolve the claim.
     * @param <T> The type of the claim.
     * @return The claim extracted from the JWT token.
     */
    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    /**
     * The generateAccessToken method generates an access token for the user.
     * @param extraClaims The extra claims to include in the access token.
     * @param userDetails The user details for which to generate the access token.
     * @return The access token generated for the user.
     */
    public String generateAccessToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, accessExpiration);
    }

    /**
     * The generateRefreshToken method generates a refresh token for the user.
     * @param userDetails The user details for which to generate the refresh token.
     * @return The refresh token generated for the user.
     */
    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    /**
     * The isTokenValid method checks if the JWT token is valid.
     * @param jwt The JWT token to validate.
     * @param userDetails The user details to validate the JWT token against.
     * @return True if the JWT token is valid, false otherwise.
     */
    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String email = extractEmail(jwt);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(jwt);
    }

    /**
     * The isTokenExpired method checks if the JWT token is expired.
     * @param jwt The JWT token to check for expiration.
     * @return True if the JWT token is expired, false otherwise.
     */
    public boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    /**
     * The buildToken method builds a JWT token with the given claims, user details, and expiration time.
     * @param extraClaims The extra claims to include in the JWT token.
     * @param userDetails The user details for which to build the JWT token.
     * @param expiration The expiration time for the JWT token.
     * @return The JWT token built with the given claims, user details, and expiration time.
     */
    private String buildToken (
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * The extractAllClaims method extracts all claims from the JWT token.
     * @param jwt The JWT token from which to extract the claims.
     * @return The claims extracted from the JWT token.
     */
    private Claims extractAllClaims(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * The getSigningKey method gets the signing key used to sign the JWT tokens.
     * @return The signing key used to sign the JWT tokens.
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
