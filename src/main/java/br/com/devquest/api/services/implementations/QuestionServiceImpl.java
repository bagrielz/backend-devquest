package br.com.devquest.api.services.implementations;

import br.com.devquest.api.enums.Difficulty;
import br.com.devquest.api.enums.Technology;
import br.com.devquest.api.model.dtos.QuestionDTO;
import br.com.devquest.api.model.entities.Question;
import br.com.devquest.api.model.entities.User;
import br.com.devquest.api.repositories.QuestionRepository;
import br.com.devquest.api.repositories.UserRepository;
import br.com.devquest.api.services.IQuestionService;
import br.com.devquest.api.services.generators.QuestionGenerator;
import br.com.devquest.api.utils.TokenJWTDecoder;
import org.springframework.stereotype.Service;

import java.util.List;
import static br.com.devquest.api.mappers.DozerMapper.*;

@Service
public class QuestionServiceImpl implements IQuestionService {

  private QuestionRepository repository;
  private UserRepository userRepository;
  private TokenJWTDecoder tokenJWTDecoder;
  private QuestionGenerator questionGenerator;

  public QuestionServiceImpl(QuestionRepository repository,
                             UserRepository userRepository,
                             TokenJWTDecoder tokenJWTDecoder,
                             QuestionGenerator questionGenerator) {

    this.repository = repository;
    this.userRepository = userRepository;
    this.tokenJWTDecoder = tokenJWTDecoder;
    this.questionGenerator = questionGenerator;
  }

  @Override
  public QuestionDTO generateQuestion(String token, Technology technology, Difficulty difficulty) {
    User user = userRepository.findByUsername(tokenJWTDecoder.getUsernameByToken(token));
    Question question;
    List<Question> questionsWithSameTechnologyAndDifficulty =
            repository.findByTechnologyAndDifficulty(technology, difficulty);

    if (!questionsWithSameTechnologyAndDifficulty.isEmpty()) {
      question = searchForQuestionNotAnsweredByUser(questionsWithSameTechnologyAndDifficulty, user);
      if (question != null) return parseObject(question, QuestionDTO.class);
    }

    question = questionGenerator.createAndSave(technology, difficulty);
    return parseObject(question, QuestionDTO.class);
  }

  private Question searchForQuestionNotAnsweredByUser(List<Question> questions, User user) {
    return questions.stream()
            .filter(q -> repository.questionWasNotAnsweredByUser(q.getId(), user.getId()))
            .findFirst()
            .orElse(null);
  }

}
