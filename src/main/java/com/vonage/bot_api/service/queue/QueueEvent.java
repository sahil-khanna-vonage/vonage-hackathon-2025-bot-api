package com.vonage.bot_api.service.queue;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QueueEvent {

  @NotNull
  private String action;

  @NotNull
  private Map<String, Object> data;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public QueueEvent(String json) throws JsonProcessingException {
    QueueEvent deserializedEvent = objectMapper.readValue(json, QueueEvent.class);
    this.action = deserializedEvent.action;
    this.data = deserializedEvent.data;
  }

  @Override
  public String toString() {
    try {
      Map<String, Object> jsonObject = new HashMap<>();
      jsonObject.put("action", action);

      if (data != null) {
        jsonObject.put("data", data);
      }
      return objectMapper.writeValueAsString(jsonObject);
    } catch (Exception e) {
      e.printStackTrace();
      return "QueueEvent{action=" + action + ", data=" + data + "}";
    }
  }
}