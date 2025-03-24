package br.com.devquest.api.integrations.controllers;

import br.com.devquest.api.configs.TestConfigs;
import br.com.devquest.api.dtos.AccountCredentialsDTOTest;
import br.com.devquest.api.dtos.TokenDTOTest;
import br.com.devquest.api.enums.Difficulty;
import br.com.devquest.api.enums.Technology;
import br.com.devquest.api.integrations.AbstractIntegrationTest;
import br.com.devquest.api.model.dtos.QuestionDTO;
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

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuestionControllerTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;
  private static ObjectMapper mapper;
  private static AccountCredentialsDTOTest accountCredentialsDTO;
  private static String userAccessToken = "Bearer ";

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.findAndRegisterModules();
    startEntities();
  }

  @Test
  @Order(0)
  void authenticate() throws JsonProcessingException {
    specification = new RequestSpecBuilder()
            .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
            .setPort(TestConfigs.SERVER_PORT)
            .setContentType(MediaType.APPLICATION_JSON_VALUE)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

    var content = given(specification)
            .basePath(TestConfigs.AUTH_CONTROLLER_BASEPATH + "/signin")
            .body(accountCredentialsDTO)
            .when()
              .post()
            .then()
              .statusCode(200)
            .extract()
              .body()
                .asString();

    var tokenDTOTest = mapper.treeToValue(extractObjectOfJSON(content, "body"), TokenDTOTest.class);
    userAccessToken = userAccessToken + tokenDTOTest.getAccessToken();
  }

  @Test
  @Order(1)
  void mustReturnAQuestionDTO_WhenGenerateWithValidParams() throws JsonProcessingException {
    var content = given(specification)
            .basePath(TestConfigs.QUESTION_CONTROLLER_BASEPATH + "/generate")
            .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, userAccessToken)
            .param("technology", Technology.JAVA)
            .param("difficulty", Difficulty.BASICO)
            .when()
              .get()
            .then()
              .statusCode(200)
            .extract()
              .body()
                .asString();

    var questionDTO = mapper.readValue(content, QuestionDTO.class);

    assertEquals(Technology.JAVA, questionDTO.getTechnology());
    assertEquals(Difficulty.BASICO, questionDTO.getDifficulty());
    assertEquals(4, questionDTO.getOptions().size());
    assertNotEquals("", questionDTO.getOptions().get(0).getIndicator());
    assertNotEquals("", questionDTO.getOptions().get(0).getText());
  }

  private static JsonNode extractObjectOfJSON(String content, String nodeObject) throws JsonProcessingException {
    JsonNode rootNode = mapper.readTree(content);
    return rootNode.get(nodeObject);
  }

  private static void startEntities() {
    accountCredentialsDTO = AccountCredentialsDTOTest.builder()
            .username("newmsimeaor")
            .password("123")
            .build();
  }

}