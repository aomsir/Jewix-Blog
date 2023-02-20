package com.aomsir.jewixapi;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import com.aomsir.jewixapi.utils.BlogPasswordEncoder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class JewixApiApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(JewixApiApplicationTests.class);
	@Resource
	BlogPasswordEncoder blogPasswordEncoder;

	@Test
	void contextLoads() {
	}

	@Test
	public void testEncoder() {
		String encode = this.blogPasswordEncoder.encode("123456" + "a04879dd-b2d0-47ba-8027-e40d254ba265");
		log.info("encode is {}", encode);
	}

}
