package com.vonage.bot_api.service.other;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.vonage.bot_api.restapi.RestApiClient;
import com.vonage.bot_api.restapi.RestApiPayload;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WhatsAppService {

  @Value("${whatsapp.api.url}")
  private String apiUrl;

  @Value("${whatsapp.api.key}")
  private String apiKey;

  @Value("${whatsapp.api.secret}")
  private String apiSecret;

  @Value("${whatsapp.sender}")
  private String sender;

  @Value("${whatsapp.receiver}")
  private String receiver;

  private final RestApiClient restApiClient;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public void send(String text) {
    RestApiPayload apiPayload = new RestApiPayload();
    apiPayload.setUrl(apiUrl);
    apiPayload.setMethod(HttpMethod.POST);
    
    HttpHeaders headers = new HttpHeaders();
    headers.setBasicAuth(apiKey, apiSecret);
    apiPayload.setHeaders(headers);
    apiPayload.addDefaultHeaders();

    apiPayload.setResponseType(Map.class);

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("message_type", "text");
    requestBody.put("channel", "whatsapp");
    requestBody.put("from", sender);
    requestBody.put("to", receiver);
    requestBody.put("text", text);

    apiPayload.setBody(requestBody);

    ResponseEntity<Map<String, Object>> response = restApiClient.execute(apiPayload);
    if (response.getStatusCode().isError()) {
      logger.error("Failed to send WhatsApp message. {}", response.getStatusCode());
    }
  }
  
}
