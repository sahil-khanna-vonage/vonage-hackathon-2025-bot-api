package com.vonage.bot_api.restapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SqlCoderRestApiClient extends RestApiClient {

  // TODO: Check why this is null, not picking from application.yml
  @Value("${sqlcoder.url}")
  private String serverUrl = "http://localhost:1235";

  public SqlCoderRestApiClient(RestTemplate restTemplate) {
    super(restTemplate);
  }

  public String buildUrl(String path) {
    return serverUrl + "/" + path;
  }

  private HttpHeaders defaultHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return headers;
  }

  public Map<String, Object> setQuestion(String question) {
    Map<String, Object> body = new HashMap<>();
    body.put("question", question);
    body.put("previous_context", List.of());
    body.put("api_key", null);
    return body;
  }

  @Override
  public <T> ResponseEntity<T> execute(RestApiPayload payload) {
    if (payload.getHeaders() == null) {
      payload.setHeaders(new HttpHeaders());
    }
    payload.getHeaders().putAll(defaultHeaders());
    return super.execute(payload);
  }
}
