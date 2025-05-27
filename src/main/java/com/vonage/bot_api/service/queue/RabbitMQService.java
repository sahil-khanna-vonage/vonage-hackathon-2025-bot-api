package com.vonage.bot_api.service.queue;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService implements MessageQueueService {

  private final AmqpTemplate amqpTemplate;

  public RabbitMQService(AmqpTemplate amqpTemplate) {
    this.amqpTemplate = amqpTemplate;
  }

  @Override
  public void sendMessage(String queueName, QueueEvent event) {
    amqpTemplate.convertAndSend(queueName, event.toString());
  }
}
