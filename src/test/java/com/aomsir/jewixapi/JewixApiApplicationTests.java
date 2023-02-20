package com.aomsir.jewixapi;

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

	@Test
	void contextLoads() {
	}

	@Test
	public void testEncoder() {
		String encode = passwordEncoder.encode("123456");
		System.out.println("encode = " + encode);
	}

}
