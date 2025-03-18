package br.com.devquest.api.model.entities;

import br.com.devquest.api.enums.Difficulty;
import br.com.devquest.api.enums.Technology;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "question")
public class Question implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Technology technology;
  private Difficulty difficulty;
  private String text;
  private String correctAnswer;
  private String justification;
  private Date createdAt;
  private List<QuestionOption> options;
  private List<User> users;

}
