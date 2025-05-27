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
@Schema(name = "Profile")
public class ProfileDto {

  private String name;

  public ProfileDto(Map<String, Object> map) {
    this.name = (String) map.get("name");
  }

  public Map<String, Object> toMap() {
    return Map.of(
        "name", name);
  }
}
