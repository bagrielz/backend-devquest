package br.com.devquest.api.services.implementations;

import br.com.devquest.api.model.dtos.UserInfoDTO;
import br.com.devquest.api.repositories.UserRepository;
import br.com.devquest.api.services.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService, IUserService {

  private UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = repository.findByUsername(username);
    if (user != null) return user;
    else throw new UsernameNotFoundException("Usuário " +username+ " não encontrado!");
  }

  @Override
  public UserInfoDTO getUserInfo(String token) {
    return null;
  }

}
