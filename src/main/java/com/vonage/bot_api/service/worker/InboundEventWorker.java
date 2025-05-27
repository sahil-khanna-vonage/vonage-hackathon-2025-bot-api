package com.vonage.bot_api.service.worker;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vonage.bot_api.common.QueueActions;
import com.vonage.bot_api.common.QueueNames;
import com.vonage.bot_api.dto.InboundEventDto;
import com.vonage.bot_api.restapi.RestApiPayload;
import com.vonage.bot_api.restapi.SqlCoderRestApiClient;
import com.vonage.bot_api.service.queue.QueueEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InboundEventWorker extends WorkerBase {
  private final RestTemplate restTemplate;
  private static final Logger logger = LoggerFactory.getLogger(InboundEventWorker.class);

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
    SqlCoderRestApiClient sqlCoderRestApiClient = new SqlCoderRestApiClient(restTemplate);
    RestApiPayload apiPayload = new RestApiPayload();
    apiPayload.setUrl(sqlCoderRestApiClient.buildUrl("query"));
    apiPayload.setMethod(HttpMethod.POST);
    apiPayload.setBody(sqlCoderRestApiClient.setQuestion(inboundEventDto.getText()));

    try {
      ResponseEntity<String> response = sqlCoderRestApiClient.execute(apiPayload);
      System.out.println(response.getBody());
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }
}