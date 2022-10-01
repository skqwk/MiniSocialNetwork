package ru.skqwk.simplesocialnetwork.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.skqwk.simplesocialnetwork.dto.GetMessageDTO;
import ru.skqwk.simplesocialnetwork.dto.MessageDTO;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MessageControllerTest extends BaseControllerTest {

  @Test
  void shouldGetNewMessageAfterSent() throws Exception {
    String friendEmail = generateEmail();
    String authTokenFriend = registerUser(friendEmail).getAuthToken();

    MessageDTO message = MessageDTO.builder().content("Simple message").build();

    String userEmail = generateEmail();
    String authToken = registerUser(userEmail).getAuthToken();

    mockMvc
        .perform(
            post("/user/message/{friendEmail}", friendEmail)
                .header(AUTHORIZATION, config.getTokenPrefix() + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(message)))
        .andExpect(status().isOk());

    String stringMessages =
        mockMvc
            .perform(
                get("/user/messages/{userEmail}", userEmail)
                    .header(AUTHORIZATION, config.getTokenPrefix() + authTokenFriend))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    GetMessageDTO[] messages = mapper.readValue(stringMessages, GetMessageDTO[].class);
    Assertions.assertEquals(1, messages.length);
  }

  @Test
  void shouldSentMessage() throws Exception {
    String email = generateEmail();
    String friendAuthToken = registerUser(email).getAuthToken();

    MessageDTO message = MessageDTO.builder().content("Simple message").build();
    String authToken = registerUser(generateEmail()).getAuthToken();

    mockMvc
        .perform(
            post("/user/message/{email}", email)
                .header(AUTHORIZATION, config.getTokenPrefix() + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(message)))
        .andExpect(status().isOk());
  }
}
