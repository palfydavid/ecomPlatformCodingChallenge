package allib.platform.ecom.integration;

import allib.platform.ecom.dto.ProductRequestDto;
import allib.platform.ecom.model.Cart;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    HttpHeaders headers = new HttpHeaders();

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setup() {
        baseUrl = baseUrl + ":" + port + "/carts";
    }

    @Test
    void create_201() {
        ProductRequestDto productRequest = new ProductRequestDto(1L, 10);
        HttpEntity<ProductRequestDto> entity = new HttpEntity<ProductRequestDto>(productRequest, headers);

        ResponseEntity<Cart> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, Cart.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    void create_404() {
        ProductRequestDto productRequest = new ProductRequestDto(5L, 10);
        HttpEntity<ProductRequestDto> entity = new HttpEntity<ProductRequestDto>(productRequest, headers);

        assertThrows(RuntimeException.class, () -> {
            ResponseEntity<Cart> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, Cart.class);
        });
    }

}
