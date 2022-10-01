package ru.skqwk.simplesocialnetwork.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skqwk.simplesocialnetwork.model.Message;
import ru.skqwk.simplesocialnetwork.model.UserAccount;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
  List<Message> findAllByFromAndTo(UserAccount from, UserAccount to);
}
