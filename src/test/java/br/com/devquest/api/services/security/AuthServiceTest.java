package br.com.devquest.api.services.security;

import br.com.devquest.api.exceptions.InvalidCredentialsException;
import br.com.devquest.api.mocks.MockAccountCredentialsDTO;
import br.com.devquest.api.mocks.MockTokenDTO;
import br.com.devquest.api.mocks.MockUser;
import br.com.devquest.api.model.dtos.security.AccountCredentialsDTO;
import br.com.devquest.api.model.dtos.security.TokenDTO;
import br.com.devquest.api.model.entities.User;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  private MockAccountCredentialsDTO accountCredentialsDTOInput;
  private MockUser userInput;
  private MockTokenDTO tokenDTOInput;
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
    accountCredentialsDTOInput = new MockAccountCredentialsDTO();
    userInput = new MockUser();
    tokenDTOInput = new MockTokenDTO();
  }

  @Test
  void signinWithValidCredentials() {
    AccountCredentialsDTO accountWithValidCredentials = accountCredentialsDTOInput.generateAccountCredentialsDTO(0);
    User user = userInput.mockUser(1);
    TokenDTO tokenDTO = tokenDTOInput.mockTokenDTO(1);

    when(userRepository.findByUsername(accountWithValidCredentials.getUsername())).thenReturn(user);
    when(tokenProvider.createAccessToken(anyString(), anyList())).thenReturn(tokenDTO);
    var result = service.signin(accountWithValidCredentials);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(true, result.getBody().getAuthenticated());
    assertEquals(tokenDTO.getUsername(), result.getBody().getUsername());
    assertEquals(tokenDTO.getAccessToken(), result.getBody().getAccessToken());
    assertEquals(tokenDTO.getRefreshToken(), result.getBody().getRefreshToken());
  }

  @Test
  void signinWithInvalidCredentials() {
    AccountCredentialsDTO credentialsWithIncorrectUsernameOrPassword = accountCredentialsDTOInput.generateAccountCredentialsDTO(1);
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