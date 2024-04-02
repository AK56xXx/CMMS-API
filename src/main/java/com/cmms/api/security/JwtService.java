package com.cmms.api.security;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	// JWT value from application.properties
	@Value("${jwt-secret}")
	private String SECRET_KEY;

	// JWT expiration value from application.properties
	@Value("${jwt-expiration-milliseconds}")
	private int EXPIRATION_TIME;

	// using the custom extraction to extract only the username
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// username valid check
	public boolean isValid(String token, UserDetails user) {
		String username = extractUsername(token);
		return username.equals(user.getUsername()) && !isTokenExpired(token);
	}

	// expiration check
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// extracting Expiration date from token
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	// custom extraction method
	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
		Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
	}

	// extract all token properties
	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()
				.verifyWith(getSigninKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();

	}

	// generating token with the secret key (signing key)
	public String generateToken(User user) {
		String token = Jwts
				.builder()
				.subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 24h
				.signWith(getSigninKey())
				.compact();

		return token;

	}

	// signing key using secret key from application.properties
	private SecretKey getSigninKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
