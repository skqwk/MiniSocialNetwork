package ru.skqwk.simplesocialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
  @NotBlank(message = "Message should be not blank")
  private String content;
}
