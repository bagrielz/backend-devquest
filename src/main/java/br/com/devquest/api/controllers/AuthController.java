package br.com.devquest.api.controllers;

import br.com.devquest.api.model.dtos.security.AccountCredentialsDTO;
import br.com.devquest.api.services.security.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  private boolean credentialsAreInvalid(AccountCredentialsDTO credentialsDTO) {
    return  credentialsDTO == null ||
            StringUtils.isBlank(credentialsDTO.getUsername()) ||
            StringUtils.isBlank(credentialsDTO.getPassword());
  }

}
