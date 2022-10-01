package ru.skqwk.simplesocialnetwork.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skqwk.simplesocialnetwork.dto.FriendDTO;
import ru.skqwk.simplesocialnetwork.model.Friends;
import ru.skqwk.simplesocialnetwork.model.UserAccount;
import ru.skqwk.simplesocialnetwork.repo.FriendsRepository;
import ru.skqwk.simplesocialnetwork.service.FriendsService;
import ru.skqwk.simplesocialnetwork.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendsServiceImpl implements FriendsService {

  private final FriendsRepository friendsRepository;
  private final UserService userService;

  @Override
  public List<FriendDTO> getAllFriends(Long id) {
    UserAccount user = userService.findUser(id);
    return friendsRepository.findAllByUser1(user).stream()
        .map(this::mapFriendsToFriendDTO)
        .collect(Collectors.toList());
  }

  @Override
  public void addFriend(Long id, Long friendId) {
    UserAccount user1 = userService.findUser(id);
    UserAccount user2 = userService.findUser(friendId);
    friendsRepository.save(Friends.builder().user1(user1).user2(user2).build());
  }

  private FriendDTO mapFriendsToFriendDTO(Friends friends) {
    UserAccount userAccount = friends.getUser2();
    return FriendDTO.builder().email(userAccount.getEmail()).id(userAccount.getId()).build();
  }
}
