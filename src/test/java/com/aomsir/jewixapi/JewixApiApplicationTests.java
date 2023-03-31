package com.aomsir.jewixapi;

import com.aomsir.jewixapi.mapper.CategoryMapper;
import com.aomsir.jewixapi.mapper.UserMapper;
import com.aomsir.jewixapi.pojo.dto.CategoryListDTO;
import com.aomsir.jewixapi.utils.JwtUtils;
import com.aomsir.jewixapi.utils.NetUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class JewixApiApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(JewixApiApplicationTests.class);


	@Resource
	private PasswordEncoder passwordEncoder;

	@Resource
	private UserMapper userMapper;

	@Resource
	private CategoryMapper categoryMapper;

	@Resource
	private NetUtils netUtils;

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
		log.error("{}", count);
	}

	@Test
	public void testNetUtilsForAgent() {
		String agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36";
		System.out.println("this.netUtils.parseUserAgent(agent) = " + this.netUtils.parseUserAgent(agent));
	}

	@Test
	public void testNetUtilsForIp() throws JsonProcessingException {
		String ip = "194.99.79.36";
		System.out.println("this.netUtils.getLocationInfo(ip) = " + this.netUtils.getLocationInfo(ip));
	}

	@Test
	public void testCategoryList() {
		List<CategoryListDTO> categoryListDTOS =
				this.categoryMapper.queryCategoryList();
		System.out.println("categoryListDTOS = " + categoryListDTOS);
	}
}