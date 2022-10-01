package ru.skqwk.simplesocialnetwork.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skqwk.simplesocialnetwork.dto.GetMessageDTO;
import ru.skqwk.simplesocialnetwork.dto.MessageDTO;
import ru.skqwk.simplesocialnetwork.model.UserAccount;
import ru.skqwk.simplesocialnetwork.service.MessageService;

import java.util.List;

@RestController
@AllArgsConstructor
@Api(tags = "Контроллер работы с сообщениями")
public class MessageController extends BaseController {

  private final MessageService messageService;

  @GetMapping("/user/messages/{userEmail}")
  @ApiOperation(value = "Получение списка сообщений текущего пользователя")
  public List<GetMessageDTO> getAllMessages(
      @AuthenticationPrincipal UserAccount userAccount, @PathVariable String userEmail) {
    return messageService.getAllMessages(userAccount.getId(), userEmail);
  }

  @PostMapping("/user/message/{userEmail}")
  @ApiOperation(value = "Отправка сообщений другому пользователю")
  public void sentMessage(
      @AuthenticationPrincipal UserAccount userAccount,
      @PathVariable String userEmail,
      @RequestBody MessageDTO message) {
    messageService.sentMessage(userAccount.getId(), userEmail, message);
  }
}
