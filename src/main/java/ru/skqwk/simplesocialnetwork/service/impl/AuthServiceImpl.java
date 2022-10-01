package ru.skqwk.simplesocialnetwork.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.skqwk.simplesocialnetwork.config.security.JwtTokenUtil;
import ru.skqwk.simplesocialnetwork.dto.UserCredentials;
import ru.skqwk.simplesocialnetwork.service.AuthService;
import ru.skqwk.simplesocialnetwork.service.UserService;

/** Реализация сервиса для авторизации пользователя. */
@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserService userService;

  /**
   * Аутентифицирует пользователя по логину и паролю, возвращает jwt-токен.
   *
   * @param userCredentials Реквизиты пользователя.
   */
  @Override
  public String authenticate(UserCredentials userCredentials) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              userCredentials.getEmail(), userCredentials.getPassword()));
    } catch (BadCredentialsException ex) {
      log.warn("User with email = {} can't auth", userCredentials.getEmail());
      throw ex;
    }
    UserDetails userDetails = userService.loadUserByUsername(userCredentials.getEmail());
    log.info("User with email = {} successfully auth", userCredentials.getEmail());

    return jwtTokenUtil.generateToken(userDetails);
  }
}
