package br.com.devquest.api.services.implementations;

import br.com.devquest.api.enums.Difficulty;
import br.com.devquest.api.enums.Technology;
import br.com.devquest.api.model.dtos.ExerciseDTO;
import br.com.devquest.api.repositories.ExerciseRepository;
import br.com.devquest.api.services.IExerciseService;
import org.springframework.stereotype.Service;

@Service
public class ExerciseServiceImpl implements IExerciseService {

  private ExerciseRepository repository;

  public ExerciseServiceImpl(ExerciseRepository repository) {
    this.repository = repository;
  }

  @Override
  public ExerciseDTO generateExercise(String token, Technology technology, Difficulty difficulty) {
    return null;
  }

}
