package com.security.config;

import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class H2Runner implements ApplicationRunner {

  private final DataSource dataSource;
  private final JdbcTemplate jdbcTemplate;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    try (Connection connection = dataSource.getConnection()) {
      log.info("connection {}", connection);
      String URL = connection.getMetaData().getURL();
      log.info("URL {}", URL);
      String User = connection.getMetaData().getUserName();
      log.info("User {}", User);
      Statement statement = connection.createStatement();
      String sql = "CREATE TABLE USER(ID INTEGER NOT NULL, NAME VARCHAR(255), PRIMARY KEY (ID) )";
      statement.executeUpdate(sql);
    }
    jdbcTemplate.execute("INSERT INTO USER VALUES(1, 'testuser')");
  }
}

