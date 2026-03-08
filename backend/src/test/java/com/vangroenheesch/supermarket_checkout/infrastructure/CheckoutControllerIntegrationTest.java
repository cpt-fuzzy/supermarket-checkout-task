package com.vangroenheesch.supermarket_checkout.infrastructure;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("POST /api/checkout")
class CheckoutControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Test
  @DisplayName("returns receipt with offer applied for 2 apples")
  void checkoutWithOffer() throws Exception {
    mockMvc
        .perform(
            post("/api/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                                        {"items": [{"productSku": "apple", "quantity": 2}]}
                                        """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.total").value(0.45))
        .andExpect(jsonPath("$.saved").value(0.15))
        .andExpect(jsonPath("$.lines.length()").value(1))
        .andExpect(jsonPath("$.lines[0].offerDescription").value("2 for 0.45"));
  }

  @Test
  @DisplayName("returns receipt for mixed cart with correct totals")
  void checkoutMixedCart() throws Exception {
    mockMvc
        .perform(
            post("/api/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                                        {"items": [
                                        {"productSku": "apple", "quantity": 2},
                                        {"productSku": "orange", "quantity": 1},
                                        {"productSku": "banana", "quantity": 3}
                                        ]}
                                        """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.total").value(2.05))
        .andExpect(jsonPath("$.saved").value(0.65))
        .andExpect(jsonPath("$.lines.length()").value(3));
  }

  @Test
  @DisplayName("returns 400 for empty list")
  void rejectsEmptyItems() throws Exception {
    mockMvc
        .perform(
            post("/api/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                                {"items": []}
                                """))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("returns 400 for missing request body")
  void rejectsMissingBody() throws Exception {
    mockMvc
        .perform(post("/api/checkout").contentType(MediaType.APPLICATION_JSON).content("{}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("returns 404 for unknown product SKU")
  void rejectsUnknownSku() throws Exception {
    mockMvc
        .perform(
            post("/api/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                                {"items": [{"productSku": "unknown", "quantity": 2}]}
                                """))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("Product not found: unknown"));
  }

  @Test
  @DisplayName("returns 400 for malformed JSON body")
  void rejectsMalformedJson() throws Exception {
    mockMvc
        .perform(
            post("/api/checkout").contentType(MediaType.APPLICATION_JSON).content("invalid json"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error").value("Malformed request body"));
  }
}
