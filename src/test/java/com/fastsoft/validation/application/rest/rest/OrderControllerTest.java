package com.fastsoft.validation.application.rest.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastsoft.validation.domain.order.OrderId;
import com.fastsoft.validation.domain.product.Product;
import com.fastsoft.validation.domain.product.ProductId;
import com.fastsoft.validation.infrastructure.adapter.input.rest.data.request.CreateOrderItemRequest;
import com.fastsoft.validation.infrastructure.adapter.input.rest.data.request.CreateOrderRequest;
import com.fastsoft.validation.infrastructure.adapter.input.rest.data.response.CreateOrderResponse;
import com.jayway.jsonpath.JsonPath;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @DisplayName("Заказ должен содержать хотя бы одну позицию")
  @Test
  void mustHaveAtLeastOneItemInOrder_Test() throws Exception {

    List<CreateOrderItemRequest> orderItems = List.of();
    CreateOrderRequest createOrderRequest = new CreateOrderRequest(orderItems);

    String json = objectMapper.writeValueAsString(createOrderRequest);

    mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isBadRequest())
        .andDo(print())
        .andExpect(
            content().json("{\"orderItems\":\"The array must contain at least one element.\"}"))
        .andReturn();
  }

  @DisplayName("Проверка общей стоимости и количества товара")
  @Test
  void totalPriceAndQuantity_Test() throws Exception {

    List<CreateOrderItemRequest> orderItems = List.of(
        createOrderItemRequest(
            "682c3a17-e4fe-4dee-baab-e3e707326797",
            "Milk",
            99.95,
            1
        ),
        createOrderItemRequest(
            "682c3a17-e4fe-4dee-baab-e3e707326791",
            "Bread",
            78.05,
            1
        )
    );
    CreateOrderRequest createOrderRequest = new CreateOrderRequest(orderItems);

    String json = objectMapper.writeValueAsString(createOrderRequest);
    mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isBadRequest())
        .andDo(print())
        .andExpect(
            content()
                .json(
                    "{\"orderItems[0].price\":\"Общая цена товара ниже чем 80,00\",\"orderItems[1].price\":\"Общая цена товара ниже чем 80,00\",\"orderItems[1].quantity\":\"Количество товара ниже чем 2\",\"orderItems[0].quantity\":\"Количество товара ниже чем 2\"}"))
        .andReturn();
  }

  @DisplayName("Не достаточно товара на складе")
  @Test
  void notEnoughInventory_Test() throws Exception {

    List<CreateOrderItemRequest> orderItems = List.of(
        createOrderItemRequest(
            "682c3a17-e4fe-4dee-baab-e3e707326797",
            "Milk",
            99.95,
            3
        ),
        createOrderItemRequest(
            "682c3a17-e4fe-4dee-baab-e3e707326797",
            "Bread",
            78.05,
            3
        )
    );
    CreateOrderRequest createOrderRequest = new CreateOrderRequest(orderItems);

    String json = objectMapper.writeValueAsString(createOrderRequest);
    mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isBadRequest())
        .andDo(print())
        .andExpect(
            content()
                .json(
                    "{\"orderItems[1].product\":\"Не достаточно товара на складе\", \"orderItems[0].product\":\"Не достаточно товара на складе\"}"))
        .andReturn();
  }

  @DisplayName("Добавить заказ")
  @Test
  void addOrder_Test() throws Exception {

    List<CreateOrderItemRequest> orderItems = List.of(
        createOrderItemRequest(
            "682c3a17-e4fe-4dee-baab-e3e707326797",
            "Milk",
            99.95,
            2
        ),
        createOrderItemRequest(
            "682c3a17-e4fe-4dee-baab-e3e707326791",
            "Bread",
            78.05,
            2
        )
    );
    CreateOrderRequest createOrderRequest = new CreateOrderRequest(orderItems);

    String json = objectMapper.writeValueAsString(createOrderRequest);
    MvcResult mvcResult = mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    String jsonResponse = mvcResult.getResponse().getContentAsString();

    CreateOrderResponse actualResponse = objectMapper.readValue(jsonResponse,
        CreateOrderResponse.class);

    OrderId orderId = actualResponse.id();
    System.out.println();

    mockMvc.perform(get("/orders/{orderId}", orderId.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(
            content()
                .json(
                    "{\"order\":{\"id\":\"%s\",\"status\":\"CREATED\",\"orderItems\":[{\"productId\":\"682c3a17-e4fe-4dee-baab-e3e707326797\",\"price\":79.95,\"quantity\":2,\"totalPrice\":159.90},{\"productId\":\"682c3a17-e4fe-4dee-baab-e3e707326791\",\"price\":79.99,\"quantity\":2,\"totalPrice\":159.98}],\"price\":319.88}}".formatted(
                        orderId.getId())))
        .andDo(print())
        .andExpect(status().isOk());

    mockMvc.perform(get("/orders/{orderId}", orderId.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(
            content()
                .json(
                    "{\"order\":{\"id\":\"%s\",\"status\":\"CREATED\",\"orderItems\":[{\"productId\":\"682c3a17-e4fe-4dee-baab-e3e707326797\",\"price\":79.95,\"quantity\":2,\"totalPrice\":159.90},{\"productId\":\"682c3a17-e4fe-4dee-baab-e3e707326791\",\"price\":79.99,\"quantity\":2,\"totalPrice\":159.98}],\"price\":319.88}}".formatted(
                        orderId.getId())))
        .andDo(print())
        .andExpect(status().isOk());

    MvcResult result = mockMvc.perform(get("/orders"))
        .andExpect(status().isOk())
        .andReturn();

    int arrayLength = JsonPath.read(result.getResponse().getContentAsString(),
        "$.length()");
    assertEquals(1, arrayLength);
  }


  private CreateOrderItemRequest createOrderItemRequest(String id, String name, double price,
      int quantity) {

    ProductId productId = new ProductId(id);
    Product product = new Product(productId, name);
    return new CreateOrderItemRequest(
        product,
        BigDecimal.valueOf(price),
        quantity
    );
  }

}