package com.aomsir.jewixapi.config;

import com.aomsir.jewixapi.pojo.vo.LoginVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.Test;
import org.springdoc.core.GroupedOpenApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OpenApiConfigurationTest {

    private final OpenApiConfiguration configuration = new OpenApiConfiguration();

    @Test
    void webApiConfigGroupsWebApiAndMatchesAllPaths() {
        GroupedOpenApi groupedOpenApi = configuration.webApiConfig();

        assertEquals("WebAPI", groupedOpenApi.getGroup());
        assertTrue(groupedOpenApi.getPathsToMatch().contains("/**"));
    }

    @Test
    void webApiInfoExposesDocumentedMetadataAndContact() {
        OpenAPI openAPI = configuration.webApiInfo();
        Info info = openAPI.getInfo();
        Contact contact = info.getContact();

        assertEquals("Jewix博客系统-API文档", info.getTitle());
        assertEquals("Jewix博客系统,新一代博客系统", info.getDescription());
        assertEquals("1.0", info.getVersion());
        assertEquals("Aomsir", contact.getName());
        assertEquals("https://www.say521.cn/", contact.getUrl());
        assertEquals("info@say521.cn", contact.getEmail());
    }

    @Test
    void loginVoUsernameRoundTripsWithoutPolymorphicTyping() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        LoginVo source = new LoginVo();
        source.setUsername("info@say521.cn");
        source.setPassword("secret");

        String json = mapper.writeValueAsString(source);

        assertTrue(json.contains("\"username\""));
        assertFalse(json.contains("@class"));
        assertFalse(json.contains("@type"));
        assertNull(mapper.getDeserializationConfig().getDefaultTyper(mapper.constructType(LoginVo.class)));

        LoginVo restored = mapper.readValue(json, LoginVo.class);
        assertEquals("info@say521.cn", restored.getUsername());
        assertEquals("secret", restored.getPassword());
    }
}
