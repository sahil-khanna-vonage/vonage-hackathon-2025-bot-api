package com.vonage.bot_api.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OllamaResponseDto {
  private String sqlQuery;
  private String nlResponseTemplate;

  public OllamaResponseDto(String content) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      JsonNode contentNode = objectMapper.readTree(content);
      sqlQuery = contentNode.get("query").asText();
      nlResponseTemplate = contentNode.get("nl").asText();
    } catch (Exception e) {
      // Do nothing
    }
  }
}