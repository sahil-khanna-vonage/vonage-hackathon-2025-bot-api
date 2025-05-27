package com.vonage.bot_api.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "InboundEvent")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InboundEventDto {

  private String to;
  private String from;
  private String channel;

  @JsonProperty("message_uuid")
  private String messageUuid;

  private Date timestamp;

  @JsonProperty("message_type")
  private String messageType;

  private String text;
  private ImageDto image;

  @JsonProperty("context_status")
  private String contextStatus;

  private ProfileDto profile;

  @SuppressWarnings("unchecked")
  public InboundEventDto(Map<String, Object> map) {
    this.to = map.get("to").toString();
    this.from = map.get("from").toString();
    this.channel = map.get("channel").toString();
    this.messageUuid = map.get("messageUuid").toString();
    this.timestamp = new Date((long) map.get("timestamp"));
    this.messageType = map.get("messageType").toString();
    this.text = map.get("text") != null ? map.get("text").toString() : null;
    this.contextStatus = map.get("contextStatus").toString();
    this.profile = new ProfileDto((Map<String, Object>) map.get("profile"));

    Object imageObj = map.get("image");
    if (imageObj != null) {
      this.image = new ImageDto((Map<String, Object>) imageObj);
    }
  }

  public Map<String, Object> toMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("to", to);
    map.put("from", from);
    map.put("channel", channel);
    map.put("messageUuid", messageUuid);
    map.put("timestamp", timestamp);
    map.put("messageType", messageType);
    map.put("text", text);
    map.put("contextStatus", contextStatus);
    map.put("profile", profile.toMap());

    if (image != null) {
        map.put("image", image.toMap());
    }

    return map;
  }
}
