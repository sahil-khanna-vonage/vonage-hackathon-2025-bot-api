package com.vonage.bot_api.restapi;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestApiClient {

  private final RestTemplate restTemplate;

  public RestApiClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public <T> ResponseEntity<T> execute(RestApiPayload payload) {
    try {
      payload.validate();

      HttpEntity<Object> entity = new HttpEntity<>(payload.getBody(), payload.getHeaders());
      String queryString = (payload.getQueryParams() != null) ? payload.getQueryParams().toQueryString() : "";

      return restTemplate.exchange(
          payload.getUrl() + queryString,
          payload.getMethod(),
          entity,
          (Class<T>) payload.getResponseType());
    } catch (Exception e) {
      throw new RuntimeException("Error executing API request: " + e.getMessage(), e);
    }
  }
}
