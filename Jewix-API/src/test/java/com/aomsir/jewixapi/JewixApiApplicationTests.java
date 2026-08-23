package com.aomsir.jewixapi;

import com.aomsir.jewixapi.util.JwtUtils;
import com.aomsir.jewixapi.util.NetUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class JewixApiApplicationTests {

	@Resource
	private PasswordEncoder passwordEncoder;

	@Resource
	private NetUtils netUtils;

	@Test
	void contextLoads() {
	}

	@Test
	public void testEncoder() {
		String raw = "123456";
		String encode = passwordEncoder.encode(raw);
		assertTrue(passwordEncoder.matches(raw, encode));
	}

	@Test
	public void testJWT() {
		Map<String, String> claims = new HashMap<String, String>();
		claims.put("userId", "10000");
		String token = JwtUtils.getToken(claims);
		DecodedJWT decodedJWT = JwtUtils.getToken(token);
		assertEquals("10000", decodedJWT.getClaim("userId").asString());
	}

	@Test
	public void testNetUtilsForAgent() {
		String agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36";
		Map<String, String> parsed = this.netUtils.parseUserAgent(agent);
		assertTrue(parsed.get("browser").startsWith("Chrome"));
		assertTrue(parsed.get("os").contains("Windows"));
	}
}
