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

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friends")
public class Friends {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friends_sequence")
  @SequenceGenerator(
      name = "friends_sequence",
      sequenceName = "friends_sequence",
      allocationSize = 1)
  private Long id;

  @OneToOne private UserAccount user1;

  @OneToOne private UserAccount user2;
}
