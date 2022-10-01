package ru.skqwk.simplesocialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Сущность для представления аккаунта пользователю. */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDTO {
  private String email;
  private int age;
}
