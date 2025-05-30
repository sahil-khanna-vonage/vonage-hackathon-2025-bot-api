package com.vonage.bot_api.restapi;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestApiPayload {
  private String url;
  private HttpMethod method;
  private Object body;
  private HttpHeaders headers;
  private Class<?> responseType;
  private QueryParams queryParams;

  public void validate() throws IllegalArgumentException {
    if (this.url == null || this.url.isEmpty()) {
      throw new IllegalArgumentException("URL must be provided.");
    }
    if (this.method == null) {
      throw new IllegalArgumentException("HTTP method must be provided.");
    }
  }

  public void addDefaultHeaders() {
    if (headers == null) {
      headers = new HttpHeaders();
    }
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
  }
}