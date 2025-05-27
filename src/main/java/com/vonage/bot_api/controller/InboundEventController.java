package com.vonage.bot_api.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vonage.bot_api.dto.InboundEventDto;
import com.vonage.bot_api.service.domain.InboundEventService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/event")
@Validated
@AllArgsConstructor
public class InboundEventController {

  private final InboundEventService inboundEventService;

  @PostMapping("/inbound")
  @Operation(description = "Webhook for Inbound event")
  public void inboundEvent(@RequestBody InboundEventDto inboundEventDto) {
    inboundEventService.inboundEvent(inboundEventDto);
  }
}
