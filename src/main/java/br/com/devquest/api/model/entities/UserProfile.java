package br.com.devquest.api.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_profile")
public class UserProfile implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String cpf;
  private String firstName;
  private String lastName;
  private LocalDate birthDate;
  private Gender gender;
  private Date createdAt;
  private Date updatedAt;
  private User user;

}
