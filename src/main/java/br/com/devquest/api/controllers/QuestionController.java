package br.com.devquest.api.controllers;

import br.com.devquest.api.services.IQuestionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

  public IQuestionService service;

  public QuestionController(IQuestionService service) {
    this.service = service;
  }

}
