package com.vangroenheesch.supermarket_checkout.infrastructure;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("GET /api/products")
class ProdutControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Test
  @DisplayName("returns all 5 seeded products")
  void returnsAllProducts() throws Exception {
    mockMvc
        .perform(get("/api/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(5));
  }

  @Test
  @DisplayName("apple has offer info")
  void appleHasOffer() throws Exception {
    mockMvc
        .perform(get("/api/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[?(@.sku == 'apple')].offer.requiredQuantity").value(2))
        .andExpect(jsonPath("$[?(@.sku == 'apple')].offer.offerPrice").value(0.45));
  }

  @Test
  @DisplayName("orange has no offer")
  void orangeHasNoOffer() throws Exception {
    mockMvc
        .perform(get("/api/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[?(@.sku == 'orange')].offer").value((Object) null));
  }
}
