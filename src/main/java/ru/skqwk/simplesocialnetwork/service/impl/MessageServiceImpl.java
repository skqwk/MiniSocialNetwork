package ru.skqwk.simplesocialnetwork.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skqwk.simplesocialnetwork.dto.GetMessageDTO;
import ru.skqwk.simplesocialnetwork.dto.MessageDTO;
import ru.skqwk.simplesocialnetwork.model.Message;
import ru.skqwk.simplesocialnetwork.model.UserAccount;
import ru.skqwk.simplesocialnetwork.repo.MessageRepository;
import ru.skqwk.simplesocialnetwork.service.MessageService;
import ru.skqwk.simplesocialnetwork.service.UserService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final MessageRepository messageRepository;
  private final UserService userService;

  @Override
  public List<GetMessageDTO> getAllMessages(Long fromId, Long toId) {
    UserAccount from = userService.findUser(fromId);
    UserAccount to = userService.findUser(toId);

    List<Message> messages = new ArrayList<>(messageRepository.findAllByFromAndTo(from, to));
    List<Message> readMessages =
        messageRepository.findAllByFromAndTo(to, from).stream()
            .peek(m -> m.setRead(true))
            .collect(Collectors.toList());
    messages.addAll(readMessages);

    return messages.stream()
        .sorted(Comparator.comparing(Message::getSentAt))
        .map(this::mapMessageToGetMessageDTO)
        .collect(Collectors.toList());
  }

  @Override
  public void sentMessage(Long fromId, Long toId, MessageDTO message) {
    UserAccount from = userService.findUser(fromId);
    UserAccount to = userService.findUser(toId);

    messageRepository.save(
        Message.builder()
            .from(from)
            .to(to)
            .content(message.getContent())
            .isRead(false)
            .sentAt(Instant.now())
            .build());
  }

  private GetMessageDTO mapMessageToGetMessageDTO(Message message) {
    return GetMessageDTO.builder()
        .content(message.getContent())
        .sentAt(message.getSentAt())
        .isRead(message.isRead())
        .from(message.getFrom().getId())
        .to(message.getTo().getId())
        .build();
  }
}
