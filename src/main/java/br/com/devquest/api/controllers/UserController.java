package br.com.devquest.api.controllers;

import br.com.devquest.api.model.dtos.UserInfoDTO;
import br.com.devquest.api.services.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private IUserService service;

  public UserController(IUserService service) {
    this.service = service;
  }

  @GetMapping("/getUserInfo")
  public ResponseEntity<UserInfoDTO> getUserInfo(@RequestHeader("Authorization") String token) {
    return service.getUserInfo(token);
  }

}
