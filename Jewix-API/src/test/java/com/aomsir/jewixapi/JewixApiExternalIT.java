package com.aomsir.jewixapi;

import com.aomsir.jewixapi.mapper.UserMapper;
import com.aomsir.jewixapi.util.NetUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

/**
 * Requires a live MySQL blog schema and the Tencent Location API.
 * Excluded from the default Surefire unit-test phase.
 */
@Tag("integration")
@SpringBootTest
@ActiveProfiles("dev")
class JewixApiExternalIT {

	private static final Logger log = LoggerFactory.getLogger(JewixApiExternalIT.class);

	@Resource
	private UserMapper userMapper;

	@Resource
	private NetUtils netUtils;

	@Test
	public void testUserMapper() {
		Long count = this.userMapper.queryUserCount(0, "Aomsir");
		log.error("{}", count);
	}

	@Test
	public void testNetUtilsForIp() throws JsonProcessingException {
		String ip = "194.99.79.36";
		this.netUtils.getLocationInfo(ip);
	}
}
