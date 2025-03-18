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
@Table(name = "exercise")
public class Exercise implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column
  private Technology technology;

  @Enumerated(EnumType.STRING)
  @Column
  private Difficulty difficulty;

  @Column(columnDefinition = "TEXT")
  private String content;

  @Column(name = "created_at")
  private Date createdAt;

  private List<ExerciseInstruction> instructions;
  private List<User> users;

}
