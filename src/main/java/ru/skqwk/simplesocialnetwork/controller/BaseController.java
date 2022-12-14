package ru.skqwk.simplesocialnetwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.skqwk.simplesocialnetwork.dto.ErrorResponse;
import ru.skqwk.simplesocialnetwork.exception.BadInputParametersException;
import ru.skqwk.simplesocialnetwork.exception.ConflictDataException;
import ru.skqwk.simplesocialnetwork.exception.ResourceNotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class BaseController {

  @ExceptionHandler(BadInputParametersException.class)
  void handle(HttpServletResponse response, BadInputParametersException exception)
      throws IOException {
    sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, exception);
  }

  @ExceptionHandler(ConflictDataException.class)
  void handleConflictData(HttpServletResponse response, ConflictDataException exception)
      throws IOException {
    sendResponse(response, HttpServletResponse.SC_CONFLICT, exception);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  void handleNotFound(HttpServletResponse response, Exception exception) throws IOException {
    sendResponse(response, HttpServletResponse.SC_NOT_FOUND, exception);
  }

  @ExceptionHandler(IllegalStateException.class)
  void handleIllegalState(HttpServletResponse response, IllegalStateException exception)
      throws IOException {
    sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, exception);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  void handleIllegalArgument(HttpServletResponse response, IllegalArgumentException exception)
      throws IOException {
    sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, exception);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  void handleValidationException(
      HttpServletResponse response, MethodArgumentNotValidException exception) throws IOException {
    String message = exception.getBindingResult().getFieldError().getDefaultMessage();
    sendResponse(
        response, HttpServletResponse.SC_BAD_REQUEST, new BadInputParametersException(message));
  }

  void sendResponse(HttpServletResponse response, int status, Exception exception)
      throws IOException {
    log.error("Send error response: status: {}, message: {}", status, exception.getMessage());
    OutputStream out = response.getOutputStream();
    ObjectMapper mapper = new ObjectMapper();
    ErrorResponse error =
        ErrorResponse.builder().code(status).message(exception.getMessage()).build();
    response.setStatus(status);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    mapper.writeValue(out, error);
    out.flush();
  }
}
