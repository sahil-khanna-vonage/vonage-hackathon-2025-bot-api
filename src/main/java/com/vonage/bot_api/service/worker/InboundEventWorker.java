package com.vonage.bot_api.service.worker;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vonage.bot_api.common.QueueActions;
import com.vonage.bot_api.common.QueueNames;
import com.vonage.bot_api.database.DatabaseRepository;
import com.vonage.bot_api.dto.InboundEventDto;
import com.vonage.bot_api.dto.OllamaResponseDto;
import com.vonage.bot_api.service.other.OllamaService;
import com.vonage.bot_api.service.other.WhatsAppService;
import com.vonage.bot_api.service.queue.QueueEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InboundEventWorker extends WorkerBase {
  private final OllamaService ollamaService;
  private final DatabaseRepository databaseRepository;
  private final WhatsAppService whatsAppService;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  @RabbitListener(queues = QueueNames.INBOUND_EVENT_QUEUE, concurrency = "5-10")
  public void process(String json) {
    QueueEvent event;
    try {
      event = new QueueEvent(json);

      if (event.getAction().equals(QueueActions.INBOUND_EVENT_ACTION)) {
        inboundEvent(event.getData());
      }
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage());
    }
  }

  private void inboundEvent(Map<String, Object> data) {
    InboundEventDto inboundEventDto = new InboundEventDto(data);

    OllamaResponseDto ollamaResponseDto = ollamaService.ask(inboundEventDto.getText());
    String queryResponse = databaseRepository.executeRawSelect(ollamaResponseDto.getSqlQuery());
    System.out.println(inboundEventDto.getText());
    System.out.println(ollamaResponseDto.getSqlQuery());
    System.out.println(queryResponse);
    System.out.println("");
    // whatsAppService.send(queryResponse);
  }
}