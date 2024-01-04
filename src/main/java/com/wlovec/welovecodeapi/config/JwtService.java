package com.wlovec.welovecodeapi.config;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.wlovec.welovecodeapi.model.User;
import com.wlovec.welovecodeapi.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class JwtService {

	private final String ENCRIPTION_KEY = "a835a0ffa51f1aceef1088d0ca970efd03b8f10d760a07851f2d9bcdb2a38659";
	private UserService userService;

	public Map<String, String> generate(String username) {
		User user = (User) this.userService.loadUserByUsername(username);
		return this.generateJwt(user);
	}

	private Map<String, String> generateJwt(User user) {
		final long currentTime = System.currentTimeMillis();
		final long expirationTime = currentTime + 30 * 60 * 1000;
		Map<String, Object> claims = Map.of(
						"name", user.getName(),
						Claims.EXPIRATION, new Date(expirationTime),
						Claims.SUBJECT, user.getEmail());

		return Map.of("Bearer", Jwts.builder()
						.setIssuedAt(new Date(currentTime))
						.setExpiration(new Date(expirationTime))
						.setSubject(user.getUsername())
						.setClaims(claims)
						.signWith(getkey(), SignatureAlgorithm.HS256)
						.compact());
	}

	private Key getkey() {
		final byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
		return Keys.hmacShaKeyFor(decoder);
	}

	public String getUsername(String token) {
		return this.getClaim(token, Claims::getSubject);
	}

	public boolean isTokenExpired(String token) {
		Date expirationDate = this.getClaim(token, Claims::getExpiration);
		return expirationDate.before(new Date());
	}

	private <T> T getClaim(String token, Function<Claims, T> function) {
		Claims claims = getAllClaims(token);
		return function.apply(claims);
	}

	private Claims getAllClaims(String token) {
		// TODO Auto-generated method stub
		return Jwts.parserBuilder()
						.setSigningKey(this.getkey())
						.build()
						.parseClaimsJws(token)
						.getBody();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = getUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

}
