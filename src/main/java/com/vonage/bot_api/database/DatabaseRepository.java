package com.vonage.bot_api.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.AllArgsConstructor;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class DatabaseRepository {

  private final JdbcTemplate jdbcTemplate;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public List<Map<String, Object>> executeRawSelect(String sql) {
    try {
      return jdbcTemplate.queryForList(sql);
    } catch (Exception e) {
      logger.error("Error executing SQL query: {}", sql, e);
      return Collections.emptyList();
    }
  }
}
