package com.vonage.bot_api.restapi;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public class RestApiPayload {
  private String url;
  private HttpMethod method;
  private Object body;
  private HttpHeaders headers;
  private Class<?> responseType;
  private QueryParams queryParams;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public HttpMethod getMethod() {
    return method;
  }

  public void setMethod(HttpMethod method) {
    this.method = method;
  }

  public Object getBody() {
    return body;
  }

  public void setBody(Object body) {
    this.body = body;
  }

  public HttpHeaders getHeaders() {
    return headers;
  }

  public void setHeaders(HttpHeaders headers) {
    this.headers = headers;
  }

  public Class<?> getResponseType() {
    return responseType;
  }

  public void setResponseType(Class<?> responseType) {
    this.responseType = responseType;
  }

  public QueryParams getQueryParams() {
    return queryParams;
  }

  public void setQueryParams(QueryParams queryParams) {
    this.queryParams = queryParams;
  }

  public void validate() throws IllegalArgumentException {
    if (this.url == null || this.url.isEmpty()) {
      throw new IllegalArgumentException("URL must be provided.");
    }
    if (this.method == null) {
      throw new IllegalArgumentException("HTTP method must be provided.");
    }
  }
}