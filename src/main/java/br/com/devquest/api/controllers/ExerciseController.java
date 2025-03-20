package br.com.devquest.api.controllers;

import br.com.devquest.api.services.IExerciseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

  private IExerciseService service;

  public ExerciseController(IExerciseService service) {
    this.service = service;
  }

}
