package com.vonage.bot_api.service.worker;

import java.util.ArrayList;
import java.util.List;
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
    List<Map<String, Object>> result = databaseRepository.executeRawSelect(ollamaResponseDto.getSqlQuery());
    List<String> output = formatDatabaseResult(result);

    System.out.println("\n\nQuestion: " + inboundEventDto.getText());

    System.out.println("Response: \n" + String.join("", output));

    // whatsAppService.send(String.join("", output));
  }

  public List<String> formatDatabaseResult(List<Map<String, Object>> result) {
    List<String> formatted = new ArrayList<>();

    if (result == null || result.isEmpty()) {
      formatted.add("Could not find data. Please rephrase your question.");
      return formatted;
    }

    int rowCount = result.size();
    int colCount = result.get(0).size();

    // 1 Row, 1 Column
    if (rowCount == 1 && colCount == 1) {
      formatted.add(String.valueOf(result.get(0).values().iterator().next()));
    }
    // 1 Row, >1 Columns
    else if (rowCount == 1) {
      Map<String, Object> row = result.get(0);
      for (Map.Entry<String, Object> entry : row.entrySet()) {
        formatted.add("\n- *" + entry.getKey() + "*: " + entry.getValue());
      }
    }
    // >1 Rows, 1 Column
    else if (colCount == 1) {
      for (Map<String, Object> row : result) {
        formatted.add("\n- " + String.valueOf(row.values().iterator().next()));
      }
    }
    // >1 Rows, >1 Columns
    else {
      for (Map<String, Object> row : result) {
        for (Map.Entry<String, Object> entry : row.entrySet()) {
          formatted.add("\n- *" + entry.getKey() + "*: " + entry.getValue());
        }
        formatted.add("\n"); // delimiter between rows
      }
      // Remove last delimiter
      if (!formatted.isEmpty()) {
        formatted.remove(formatted.size() - 1);
      }
    }

    return formatted;
  }
}