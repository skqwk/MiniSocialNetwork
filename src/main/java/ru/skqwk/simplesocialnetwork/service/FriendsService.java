package ru.skqwk.simplesocialnetwork.service;

import ru.skqwk.simplesocialnetwork.dto.FriendDTO;

import java.util.List;

public interface FriendsService {
  List<FriendDTO> getAllFriends(Long id);

  void addFriend(Long id, String email);
}
