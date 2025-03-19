package br.com.devquest.api.controllers;

import br.com.devquest.api.services.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private IUserService service;

  public UserController(IUserService service) {
    this.service = service;
  }

}
