package br.com.devquest.api.integrations.controllers;

import br.com.devquest.api.configs.TestConfigs;
import br.com.devquest.api.dtos.AccountCredentialsDTOTest;
import br.com.devquest.api.dtos.TokenDTOTest;
import br.com.devquest.api.exceptions.response.ExceptionResponse;
import br.com.devquest.api.integrations.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;
  private static ObjectMapper mapper;
  private static AccountCredentialsDTOTest accountCredentialsDTO;
  private static AccountCredentialsDTOTest invalidAccountCredentials;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.findAndRegisterModules();
    startEntities();
  }

  @Test
  @Order(1)
  void signinWithValidCredentials() throws JsonProcessingException {
    specification = new RequestSpecBuilder()
            .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
            .setBasePath("/auth/signin")
            .setPort(TestConfigs.SERVER_PORT)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

    var content = given(specification)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(accountCredentialsDTO)
            .when()
              .post()
            .then()
              .statusCode(200)
            .extract()
              .body()
                .asString();

    JsonNode rootNode = mapper.readTree(content);
    JsonNode bodyNode = rootNode.get("body");
    var response = mapper.treeToValue(bodyNode, TokenDTOTest.class);

    assertEquals("msimeaor", response.getUsername());
    assertEquals(true, response.getAuthenticated());
    assertNotNull(response.getAccessToken());
    assertNotNull(response.getRefreshToken());
  }

  @Test
  @Order(2)
  void signinWithInvalidCredentials() throws JsonProcessingException {
    var content = given(specification)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(invalidAccountCredentials)
            .when()
              .post()
            .then()
              .statusCode(403)
            .extract()
              .body()
                .asString();

    var response = mapper.readValue(content, ExceptionResponse.class);

    assertEquals("Usuário ou senha incorretos!", response.getMessage());
  }

  @Test
  void refreshToken() {
  }

  public void startEntities() {
    accountCredentialsDTO = AccountCredentialsDTOTest.builder()
            .username("msimeaor")
            .password("123")
            .build();

    invalidAccountCredentials = AccountCredentialsDTOTest.builder()
            .username("matheus")
            .password("123")
            .build();
  }

}