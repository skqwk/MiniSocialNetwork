package ru.skqwk.simplesocialnetwork.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skqwk.simplesocialnetwork.model.Friends;
import ru.skqwk.simplesocialnetwork.model.UserAccount;

import java.util.List;

public interface FriendsRepository extends JpaRepository<Friends, Long> {
  List<Friends> findAllByUser1(UserAccount user1);
}
