package com.vonage.bot_api.service.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vonage.bot_api.dto.ChatMessageDto;
import com.vonage.bot_api.dto.OllamaResponseDto;
import com.vonage.bot_api.restapi.RestApiClient;
import com.vonage.bot_api.restapi.RestApiPayload;
import com.vonage.bot_api.utils.FileCache;
import lombok.RequiredArgsConstructor;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OllamaService {

  @Value("${ollama.url}")
  private String serverUrl;

  @Value("${ollama.model}")
  private String ollamaModel;

  private final RestApiClient restApiClient;
  private String chatHistoryFileName = "chat-history.json";

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @SuppressWarnings("unchecked")
  public OllamaResponseDto ask(String userPrompt) {
    List<ChatMessageDto> history = loadChatHistory();
    history.add(new ChatMessageDto("user", userPrompt));

    RestApiPayload apiPayload = new RestApiPayload();
    apiPayload.setUrl(serverUrl);
    apiPayload.setMethod(HttpMethod.POST);
    apiPayload.addDefaultHeaders();
    apiPayload.setResponseType(Map.class);

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("model", ollamaModel);
    requestBody.put("stream", false);
    requestBody.put("messages", history);

    apiPayload.setBody(requestBody);

    ResponseEntity<Map<String, Object>> response = restApiClient.execute(apiPayload);
    if (response.getStatusCode().isError()) {
      return new OllamaResponseDto("{query: \"\", nl: \"\"}");
    }

    Map<String, Object> responseBody = response.getBody();
    Map<String, Object> message = (Map<String, Object>) responseBody.get("message");

    String content = message.get("content").toString();
    OllamaResponseDto ollamaResponseDto = new OllamaResponseDto(content);

    history.add(new ChatMessageDto("assistant", content));
    FileCache.writeCache(chatHistoryFileName, history);

    return ollamaResponseDto;
  }

  private List<ChatMessageDto> loadChatHistory() {
    Optional<List<ChatMessageDto>> cached = FileCache.readCache(
        chatHistoryFileName,
        new TypeReference<List<ChatMessageDto>>() {
        });

    if (cached.isPresent()) {
      return cached.get();
    }

    InputStream seedPromptStream = getClass().getClassLoader().getResourceAsStream("seed-prompt.json");
    ObjectMapper mapper = new ObjectMapper();
    List<ChatMessageDto> chatHistory = null;
    try {
      chatHistory = mapper.readValue(
          seedPromptStream,
          new TypeReference<List<ChatMessageDto>>() {
          });

      ChatMessageDto seedChatMessage = chatHistory.get(0);
      seedChatMessage.appendContent(readDbSchema());
      chatHistory.remove(0);
      chatHistory.add(seedChatMessage);

      FileCache.writeCache(chatHistoryFileName, chatHistory);
    } catch (Exception e) {
      logger.error("loadChatHistory", e);
    }

    return chatHistory;
  }

  private String readDbSchema() {
    try {
      InputStream seedPromptStream = getClass().getClassLoader().getResourceAsStream("db-schema.sql");
      return new String(seedPromptStream.readAllBytes(), StandardCharsets.UTF_8);
    } catch (Exception e) {
      return "";
    }
  }
}