package br.com.devquest.api.services.security;

import br.com.devquest.api.model.dtos.security.AccountCredentialsDTO;
import br.com.devquest.api.model.dtos.security.TokenDTO;
import br.com.devquest.api.repositories.UserRepository;
import br.com.devquest.api.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private AuthenticationManager authenticationManager;
  private JwtTokenProvider tokenProvider;
  private UserRepository userRepository;

  public AuthService(AuthenticationManager authenticationManager,
                     JwtTokenProvider tokenProvider,
                     UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
    this.userRepository = userRepository;
  }

  public ResponseEntity<TokenDTO> signin(AccountCredentialsDTO credentials) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword()));

    var user = userRepository.findByUsername(credentials.getUsername());
    if (user == null) throw new UsernameNotFoundException("Usuário " +credentials.getUsername()+ " não encontrado!");
    var tokenResponse = tokenProvider.createAccessToken(credentials.getUsername(), user.getRoles());
    return ResponseEntity.ok(tokenResponse);
  }

}
