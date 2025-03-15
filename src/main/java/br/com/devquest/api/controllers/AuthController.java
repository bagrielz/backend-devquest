package br.com.devquest.api.controllers;

import br.com.devquest.api.model.dtos.security.AccountCredentialsDTO;
import br.com.devquest.api.services.security.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private AuthService service;

  public AuthController(AuthService service) {
    this.service = service;
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO credentialsDTO) {
    if (credentialsAreInvalid(credentialsDTO))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário ou senha incorretos!");
    var token = service.signin(credentialsDTO);
    if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário ou senha incorretos!");

    return ResponseEntity.ok(token);
  }

  @PutMapping("/refresh/{username}")
  public ResponseEntity<?> refreshToken(@PathVariable("username") String username,
                                        @RequestHeader("Authorization") String refreshToken) {

    if (parametersAreInvalid(username, refreshToken))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário não autenticado!");
    var token = service.refreshToken(username, refreshToken);
    if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário não autenticado!");
    return ResponseEntity.ok(token);
  }

  private boolean parametersAreInvalid(String username, String refreshToken) {
    return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
  }

  private boolean credentialsAreInvalid(AccountCredentialsDTO credentialsDTO) {
    return  credentialsDTO == null ||
            StringUtils.isBlank(credentialsDTO.getUsername()) ||
            StringUtils.isBlank(credentialsDTO.getPassword());
  }

}
