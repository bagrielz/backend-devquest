package br.com.devquest.api.services;

import br.com.devquest.api.model.dtos.UserInfoDTO;

public interface IUserService {

  UserInfoDTO getUserInfo(String token);

}
