package ru.skqwk.simplesocialnetwork.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.skqwk.simplesocialnetwork.dto.UserAccountEditDTO;
import ru.skqwk.simplesocialnetwork.dto.UserRegisterRequest;
import ru.skqwk.simplesocialnetwork.model.UserAccount;

public interface UserService extends UserDetailsService {
  UserAccount findUser(Long id);

  UserAccount findUserByEmail(String email);

  void addNewUser(UserRegisterRequest registerRequest);

  void deleteAccount(Long id);

  void updateUserAccount(Long userId, UserAccountEditDTO updatedUserAccount);
}
