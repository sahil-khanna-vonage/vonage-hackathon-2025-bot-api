package com.vonage.bot_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
  private String role;
  private String content;

  public void appendContent(String content) {
    this.content += content;
  }
}
