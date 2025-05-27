package com.vonage.bot_api.service.domain;

import org.springframework.stereotype.Service;
import com.vonage.bot_api.common.QueueActions;
import com.vonage.bot_api.common.QueueNames;
import com.vonage.bot_api.dto.InboundEventDto;
import com.vonage.bot_api.service.queue.MessageQueueService;
import com.vonage.bot_api.service.queue.QueueEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class InboundEventService {

  private final MessageQueueService messageQueueService;

  public void inboundEvent(InboundEventDto inboundEventDto) {
    messageQueueService.sendMessage(QueueNames.INBOUND_EVENT_QUEUE, new QueueEvent(QueueActions.INBOUND_EVENT_ACTION, inboundEventDto.toMap()));
  }
}
