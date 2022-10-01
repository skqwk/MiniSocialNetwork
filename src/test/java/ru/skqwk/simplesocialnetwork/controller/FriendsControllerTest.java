package ru.skqwk.simplesocialnetwork.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.skqwk.simplesocialnetwork.dto.AuthOkResponse;
import ru.skqwk.simplesocialnetwork.dto.FriendDTO;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FriendsControllerTest extends BaseControllerTest {

  @Test
  void shouldGetAllFriendAfterAdding() throws Exception {
    AuthOkResponse authOkResponse = registerUser(generateEmail());

    String friendEmail1 = generateEmail();
    registerUser(friendEmail1);

    String friendEmail2 = generateEmail();
    registerUser(friendEmail2);

    addFriend(authOkResponse, friendEmail1, 200);
    addFriend(authOkResponse, friendEmail2, 200);

    String stringFriends =
        mockMvc
            .perform(
                get("/user/friends")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(AUTHORIZATION, config.getTokenPrefix() + authOkResponse.getAuthToken()))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    FriendDTO[] friends = mapper.readValue(stringFriends, FriendDTO[].class);

    Assertions.assertEquals(2, friends.length);
  }

  @Test
  void shouldAddFriend() throws Exception {

    String friendEmail = generateEmail();
    registerUser(friendEmail);

    String userEmail = generateEmail();

    AuthOkResponse authOkResponse = registerUser(userEmail);
    addFriend(authOkResponse, friendEmail, 200);
  }

  private void addFriend(AuthOkResponse authOkResponse, String friendEmail, int expectedStatus)
      throws Exception {
    mockMvc
        .perform(
            post("/user/friend/{friendEmail}", friendEmail)
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, config.getTokenPrefix() + authOkResponse.getAuthToken()))
        .andExpect(status().is(expectedStatus));
  }
}
