package br.com.devquest.api.services.implementations;

import br.com.devquest.api.repositories.QuestionRepository;
import br.com.devquest.api.services.IQuestionService;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements IQuestionService {

  private QuestionRepository repository;

  public QuestionServiceImpl(QuestionRepository repository) {
    this.repository = repository;
  }

}
