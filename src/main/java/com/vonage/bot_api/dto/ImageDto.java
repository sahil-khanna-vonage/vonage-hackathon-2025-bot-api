package com.vonage.bot_api.dto;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Image")
public class ImageDto {

  private String url;
  private String name;

  public ImageDto(Map<String, Object> map) {
    this.name = (String) map.get("name");
    this.url = (String) map.get("url");
  }

  public Map<String, Object> toMap() {
    return Map.of(
        "url", url,
        "name", name);
  }
}
