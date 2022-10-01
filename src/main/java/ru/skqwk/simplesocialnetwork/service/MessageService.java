package ru.skqwk.simplesocialnetwork.service;

import ru.skqwk.simplesocialnetwork.dto.GetMessageDTO;
import ru.skqwk.simplesocialnetwork.dto.MessageDTO;

import java.util.List;

public interface MessageService {
  List<GetMessageDTO> getAllMessages(Long fromId, String userEmail);

  void sentMessage(Long fromId, String userEmail, MessageDTO message);
}
