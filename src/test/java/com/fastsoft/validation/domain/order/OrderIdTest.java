package com.fastsoft.validation.domain.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderIdTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldCreateObjectFromString() throws JsonProcessingException {
    String uuidString = "682c3a17-e4fe-4dee-baab-e3e707326797";
    OrderId orderId = objectMapper.readValue("\"" + uuidString + "\"", OrderId.class);
    assertThat(orderId)
        .usingRecursiveComparison()
        .isEqualTo(new OrderId(UUID.fromString(uuidString)));

  }

}