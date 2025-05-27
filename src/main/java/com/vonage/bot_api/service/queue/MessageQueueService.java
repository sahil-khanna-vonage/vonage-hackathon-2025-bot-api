package com.vonage.bot_api.service.queue;

public interface MessageQueueService {
  void sendMessage(String queueName, QueueEvent event);
}