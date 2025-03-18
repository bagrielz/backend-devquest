package br.com.devquest.api.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "exercise")
public class Exercise implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Technology technology;
  private Difficulty difficulty;
  private String content;
  private Date createdAt;
  private List<Instruction> instructions;
  private List<User> users;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Exercise exercise = (Exercise) o;
    return Objects.equals(id, exercise.id) && Objects.equals(technology, exercise.technology) && Objects.equals(difficulty, exercise.difficulty) && Objects.equals(content, exercise.content) && Objects.equals(createdAt, exercise.createdAt) && Objects.equals(instructions, exercise.instructions) && Objects.equals(users, exercise.users);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, technology, difficulty, content, createdAt, instructions, users);
  }

}
