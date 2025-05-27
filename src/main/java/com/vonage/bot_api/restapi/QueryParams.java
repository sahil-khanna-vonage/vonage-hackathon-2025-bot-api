package com.vonage.bot_api.restapi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class QueryParams {

  private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

  public QueryParams(Map<String, String> queryParams) {
    queryParams.forEach(this::add);
  }

  public QueryParams add(String key, String value) {
    if (key != null && !key.isEmpty() && value != null && !value.isEmpty()) {
      try {
        String encodedKey = URLEncoder.encode(key, "UTF-8");
        String encodedValue = URLEncoder.encode(value, "UTF-8");
        this.params.add(encodedKey, encodedValue);
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException("Error encoding query parameter", e);
      }
    }
    return this;
  }

  public String toQueryString() {
    StringBuilder queryString = new StringBuilder();

    params.forEach((key, values) -> {
      for (String value : values) {
        if (queryString.length() > 0) {
          queryString.append("&");
        }
        queryString.append(key).append("=").append(value);
      }
    });

    if (queryString.length() > 0) {
      queryString.insert(0, "?");
    }

    return queryString.toString();
  }
}