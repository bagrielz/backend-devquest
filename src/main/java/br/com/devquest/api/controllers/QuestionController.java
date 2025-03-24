package br.com.devquest.api.controllers;

import br.com.devquest.api.enums.Difficulty;
import br.com.devquest.api.enums.Technology;
import br.com.devquest.api.model.dtos.QuestionDTO;
import br.com.devquest.api.services.IQuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

  public IQuestionService service;

  public QuestionController(IQuestionService service) {
    this.service = service;
  }

  @GetMapping("/generate")
  public ResponseEntity<QuestionDTO> generate(@RequestHeader("Authorization") String token,
                                              @RequestParam("technology") Technology technology,
                                              @RequestParam("difficulty") Difficulty difficulty) {

    QuestionDTO questionDTO = service.generateQuestion(token, technology, difficulty);
    return new ResponseEntity<>(questionDTO, HttpStatus.OK);
  }

}
