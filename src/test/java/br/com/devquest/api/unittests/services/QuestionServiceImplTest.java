package br.com.devquest.api.unittests.services;

import br.com.devquest.api.enums.Difficulty;
import br.com.devquest.api.enums.Technology;
import br.com.devquest.api.model.entities.Question;
import br.com.devquest.api.model.entities.User;
import br.com.devquest.api.repositories.QuestionRepository;
import br.com.devquest.api.repositories.UserRepository;
import br.com.devquest.api.services.implementations.QuestionServiceImpl;
import br.com.devquest.api.unittests.mocks.MockQuestion;
import br.com.devquest.api.unittests.mocks.MockUser;
import br.com.devquest.api.utils.TokenJWTDecoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

  private MockQuestion questionInput;
  private MockUser userInput;
  @InjectMocks
  private QuestionServiceImpl service;
  @Mock
  private QuestionRepository repository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private TokenJWTDecoder tokenJWTDecoder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    questionInput = new MockQuestion();
    userInput = new MockUser();
  }

  @Test
  void mustReturnsANewQuestionDTO_WhenThereAreNoQuestionsWithThisTechnologyAndDifficulty() {
    Question question = questionInput.mockQuestion(1);
    User user = userInput.mockUser(1);
    when(userRepository.findByUsername(anyString())).thenReturn(user);
    when(tokenJWTDecoder.getUsernameByToken(anyString())).thenReturn(user.getUsername());
    when(repository.findByTechnologyAndDifficulty(any(Technology.class), any(Difficulty.class)))
            .thenReturn(Collections.EMPTY_LIST);
    when(questionGenerator.createAndSave(any(Technology.class), any(Difficulty.class))).thenReturn(question);

    var result = service.generateQuestion("Example of token", Technology.JAVA, Difficulty.BASICO);

    assertEquals(question.getId(), result.getId());
    assertEquals(question.getTechnology(), result.getTechnology());
    assertEquals(question.getDifficulty(), result.getDifficulty());
    assertEquals(question.getText(), result.getText());
    assertEquals(question.getCorrectAnswer(), result.getCorrectAnswer());
    assertEquals(question.getJustification(), result.getJustification());
    assertTrue(result.getOptions().size(4));
    assertEquals(1L, result.getOptions().index(0).getId());
    assertEquals("1", result.getOptions().index(0).getIndicator());
    assertEquals("Question Text " + 1, result.getOptions().index(0).getText());
  }

}