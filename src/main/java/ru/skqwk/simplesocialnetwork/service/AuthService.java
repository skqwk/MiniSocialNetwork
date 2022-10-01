package ru.skqwk.simplesocialnetwork.service;

import ru.skqwk.simplesocialnetwork.dto.UserCredentials;

public interface AuthService {
  String authenticate(UserCredentials userCredentials);
}
