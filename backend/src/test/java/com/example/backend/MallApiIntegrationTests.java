package com.example.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MallApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void publicProductEndpointsShouldReturnData() throws Exception {
        mockMvc.perform(get("/api/products/recommend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].id").exists());

        mockMvc.perform(get("/api/products/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").isString())
                .andExpect(jsonPath("$.data.skus[0].name").isString())
                .andExpect(jsonPath("$.data.reviews[0].user").isString());
    }

    @Test
    void adminShouldAccessSystemAndProductEndpoints() throws Exception {
        String adminToken = loginAndGetToken("/api/admin/login", """
                {
                  "username": "admin",
                  "password": "admin123"
                }
                """);

        mockMvc.perform(get("/api/admin/system/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.records[0].username").value("operator"));

        mockMvc.perform(get("/api/admin/product/categories/tree")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").isString());

        mockMvc.perform(get("/api/admin/product/spus")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.records[0].name").isString());
    }

    @Test
    void memberShouldAccessProfileCartAndOrderFlow() throws Exception {
        String memberToken = loginAndGetToken("/api/user/login", """
                {
                  "username": "buyer01",
                  "password": "user123"
                }
                """);

        mockMvc.perform(get("/api/user/profile")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + memberToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("buyer01"));

        mockMvc.perform(get("/api/cart/items")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + memberToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").isString());

        mockMvc.perform(get("/api/orders/confirm")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + memberToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.addresses[0].name").isString())
                .andExpect(jsonPath("$.data.items[0].name").isString());

        MvcResult orderResult = mockMvc.perform(post("/api/orders")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + memberToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "addressId": 1,
                                  "paymentMethod": "alipay",
                                  "remark": "测试下单"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.orderNo").isString())
                .andReturn();

        Long orderId = objectMapper.readTree(orderResult.getResponse().getContentAsString()).path("data").path("orderId").asLong();

        mockMvc.perform(post("/api/orders/" + orderId + "/pay")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + memberToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "paymentMethod": "alipay"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("PAID"));

        mockMvc.perform(get("/api/orders/my")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + memberToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].orderNo").isString());
    }

    private String loginAndGetToken(String path, String body) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode jsonNode = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        return jsonNode.path("data").path("token").asText();
    }
}
