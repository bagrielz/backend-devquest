package br.com.devquest.api.services.security;

import br.com.devquest.api.exceptions.InvalidCredentialsException;
import br.com.devquest.api.mocks.MockAccountCredentialsDTO;
import br.com.devquest.api.model.dtos.security.AccountCredentialsDTO;
import br.com.devquest.api.repositories.UserRepository;
import br.com.devquest.api.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  private MockAccountCredentialsDTO input;
  @InjectMocks
  private AuthService service;
  @Mock
  private JwtTokenProvider tokenProvider;
  @Mock
  private UserRepository userRepository;
  @Mock
  private AuthenticationManager authenticationManager;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    input = new MockAccountCredentialsDTO();
  }

  @Test
  void signinWithValidCredentials() {

  }

  @Test
  void signinWithInvalidCredentials() {
    AccountCredentialsDTO credentialsWithIncorrectUsernameOrPassword = input.generateAccountCredentialsDTO(1);
    when(authenticationManager.authenticate(any())).thenThrow(new InvalidCredentialsException("Usuário ou senha incorretos!"));
    Exception exception = assertThrows(InvalidCredentialsException.class, () -> {
      service.signin(credentialsWithIncorrectUsernameOrPassword);
    });

    assertTrue("Usuário ou senha incorretos!".equals(exception.getMessage()));
  }

  @Test
  void refreshToken() {
  }

}