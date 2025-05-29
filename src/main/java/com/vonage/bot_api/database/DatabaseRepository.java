package com.vonage.bot_api.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class DatabaseRepository {

  private final JdbcTemplate jdbcTemplate;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public String executeRawSelect(String sql) {
    try {
      List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

      List<String> response = new ArrayList<>();
      for (Map<String, Object> row : result) {
        for (Map.Entry<String, Object> entry : row.entrySet()) {
          response.add(entry.getKey() + " = " + entry.getValue());
        }
      }

      return String.join("\n", response);
    }
    catch (Exception e) {
      logger.error("Error executing SQL query: {}", sql, e);
      return "No data found";
    }
  }
}
