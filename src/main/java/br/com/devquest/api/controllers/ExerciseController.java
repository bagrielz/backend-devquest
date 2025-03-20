package br.com.devquest.api.controllers;

import br.com.devquest.api.enums.Difficulty;
import br.com.devquest.api.enums.Technology;
import br.com.devquest.api.model.dtos.ExerciseDTO;
import br.com.devquest.api.services.IExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

  private IExerciseService service;

  public ExerciseController(IExerciseService service) {
    this.service = service;
  }

  @GetMapping("/generate")
  public ResponseEntity<ExerciseDTO> generate(@RequestHeader("Authorization") String token,
                                              @RequestParam("technology") Technology technology,
                                              @RequestParam("difficulty") Difficulty difficulty) {

    ExerciseDTO exerciseDTO = service.generateExercise(token, technology, difficulty);
    return new ResponseEntity<>(exerciseDTO, HttpStatus.OK);
  }

}
