package ru.skqwk.simplesocialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skqwk.simplesocialnetwork.dto.UserAccountEditDTO;
import ru.skqwk.simplesocialnetwork.dto.UserRegisterRequest;
import ru.skqwk.simplesocialnetwork.enumeration.UserRole;
import ru.skqwk.simplesocialnetwork.exception.BadInputParametersException;
import ru.skqwk.simplesocialnetwork.exception.ConflictDataException;
import ru.skqwk.simplesocialnetwork.exception.ResourceNotFoundException;
import ru.skqwk.simplesocialnetwork.model.UserAccount;
import ru.skqwk.simplesocialnetwork.repo.UserRepository;
import ru.skqwk.simplesocialnetwork.service.UserService;

/**
 * Реализация сервиса для работы с пользователями (загрузка по username, удаление, создание,
 * обновление).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${manager-token}")
  private String secretManagerToken;

  /**
   * Загружает пользователя по email.
   *
   * @param email Email пользователя.
   * @throws UsernameNotFoundException Если пользователя не найден.
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository
        .findByEmail(email)
        .orElseThrow(
            () ->
                new UsernameNotFoundException(
                    String.format("User with email: %s - not found", email)));
  }

  /**
   * Находит пользователя.
   *
   * @param id Идентификатор пользователя.
   */
  @Override
  public UserAccount findUser(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException(String.format("User with id: %s - not found", id)));
  }

  /**
   * Находит пользователя.
   *
   * @param email Почтовый адрес пользователя.
   */
  @Override
  public UserAccount findUserByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    String.format("User with email: %s - not found", email)));
  }

  /**
   * Регистрация пользователя.
   *
   * @param registerRequest Данные регистрирующегося пользователя.
   * @throws ConflictDataException Если пользователь с таким email уже существует.
   */
  @Override
  public void addNewUser(UserRegisterRequest registerRequest) {
    UserRole role = defineUserRole(registerRequest.getManagerToken());
    UserAccount userAccount =
        UserAccount.builder()
            .role(role)
            .age(registerRequest.getAge())
            .email(registerRequest.getEmail())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .build();
    try {
      userRepository.save(userAccount);
    } catch (DataIntegrityViolationException ex) {
      log.warn("Attempt registration with existed email = {}", registerRequest.getEmail());
      throw new ConflictDataException(
          String.format("You can't use %s", registerRequest.getEmail()));
    }
    log.info("User with email = {} successfully registered", registerRequest.getEmail());
  }

  /**
   * Удаляет аккаунт пользователя.
   *
   * @param id Идентификатор пользователя.
   */
  @Override
  public void deleteAccount(Long id) {
    UserAccount user = findUser(id);
    userRepository.deleteById(id);
  }

  /**
   * Изменяет аккаунт пользователя.
   *
   * @param userId Идентификатор пользователя.
   * @param updatedUserAccount Обновленный аккаунт пользователя.
   */
  @Override
  public void updateUserAccount(Long userId, UserAccountEditDTO updatedUserAccount) {
    UserAccount user = findUser(userId);
    user.setPassword(passwordEncoder.encode(updatedUserAccount.getPassword()));
    user.setEmail(updatedUserAccount.getEmail());
    user.setAge(updatedUserAccount.getAge());

    userRepository.save(user);
  }

  private UserRole defineUserRole(String managerToken) {
    if (managerToken == null) {
      return UserRole.USER;
    } else if (managerToken.equals(secretManagerToken)) {
      return UserRole.MANAGER;
    } else {
      throw new BadInputParametersException("Token is invalid");
    }
  }
}
