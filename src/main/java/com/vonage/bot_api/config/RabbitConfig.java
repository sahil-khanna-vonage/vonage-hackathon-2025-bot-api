package com.vonage.bot_api.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.vonage.bot_api.common.QueueNames;

@Configuration
public class RabbitConfig {

  @Bean
  public Queue inboundEventQueue() {
    return new Queue(QueueNames.INBOUND_EVENT_QUEUE, true);
  }
}