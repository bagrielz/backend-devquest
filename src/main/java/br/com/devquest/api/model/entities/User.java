package br.com.devquest.api.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "full_name")
  private String fullname;

  @Column
  private String password;

  @Column(name = "user_name", unique = true)
  private String username;

  @Column(name = "account_non_expired")
  private Boolean accountNonExpired;

  @Column(name = "account_non_locked")
  private Boolean accountNonLocked;

  @Column(name = "credentials_non_expired")
  private Boolean credentialsNonExpired;

  @Column
  private Boolean enabled;

  private UserProfile userProfile;
  private ActivityStatistics activityStatistics;
  private List<Exercise> exercises;
  private List<Question> questions;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_permission",
    joinColumns = {@JoinColumn(name = "id_user")},
    inverseJoinColumns = {@JoinColumn(name = "id_permission")}
  )
  private List<Permission> permissions;

  public List<String> getRoles() {
    List<String> roles = new ArrayList<>();
    for (Permission permission : permissions) {
      roles.add(permission.getDescription());
    }
    return roles;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.permissions;
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

}
