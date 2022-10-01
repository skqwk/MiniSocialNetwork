package ru.skqwk.simplesocialnetwork.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skqwk.simplesocialnetwork.dto.FriendDTO;
import ru.skqwk.simplesocialnetwork.model.UserAccount;
import ru.skqwk.simplesocialnetwork.service.FriendsService;

import java.util.List;

@RestController
@AllArgsConstructor
@Api(tags = "Контроллер добавления друзей")
public class FriendsController {

  private final FriendsService friendsService;

  @GetMapping("/user/friends")
  @ApiOperation(value = "Получение списка друзей текущего пользователя")
  public List<FriendDTO> getAllFriends(@AuthenticationPrincipal UserAccount userAccount) {
    return friendsService.getAllFriends(userAccount.getId());
  }

  @PostMapping("/user/friend/{friendId}")
  @ApiOperation(value = "Добавление друзей текущего пользователя")
  public void addFriend(
      @AuthenticationPrincipal UserAccount userAccount, @PathVariable Long friendId) {
    friendsService.addFriend(userAccount.getId(), friendId);
  }
}
