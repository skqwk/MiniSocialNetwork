package ru.skqwk.simplesocialnetwork.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.Instant;

/** Сущность для представления сообщений в системе. */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_sequence")
  @SequenceGenerator(
      name = "message_sequence",
      sequenceName = "message_sequence",
      allocationSize = 1)
  private Long id;

  @OneToOne private UserAccount from;
  @OneToOne private UserAccount to;
  private boolean isRead = false;
  private String content;
  private Instant sentAt;
}
