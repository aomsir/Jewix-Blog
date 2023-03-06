package com.aomsir.jewixapi;

import com.aomsir.jewixapi.mapper.UserMapper;
import com.aomsir.jewixapi.utils.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
class JewixApiApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(JewixApiApplicationTests.class);


	@Resource
	private PasswordEncoder passwordEncoder;

	@Resource
	private UserMapper userMapper;

	@Test
	void contextLoads() {
	}

	@Test
	public void testEncoder() {
		String encode = passwordEncoder.encode("123456");
		System.out.println("encode = " + encode);
	}


	@Test
	public void testJWT() {
		DecodedJWT decodedJWT = JwtUtils.getToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzc1NDU4MDgsInVzZXJJZCI6IjEwMDAwIn0.Bs3EFJ3kFbyyJRAme_3avPjU-dCHrje9WS4pzx6sC94");
		String payload = decodedJWT.getPayload();
		System.out.println("payload = " + payload);
	}

	@Test
	public void testUserMapper() {
		Long count = this.userMapper.queryUserCount(0, "Aomsir");
		log.error("{}",count);
	}

}
