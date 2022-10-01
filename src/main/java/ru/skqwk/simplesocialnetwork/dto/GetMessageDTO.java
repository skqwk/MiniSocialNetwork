package ru.skqwk.simplesocialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMessageDTO {
  private Long from;
  private Long to;
  private String content;
  private Boolean isRead;
  private Instant sentAt;
}
