package allib.platform.ecom.controller.cart;

import allib.platform.ecom.dto.CheckoutStateRequestDto;
import allib.platform.ecom.dto.ProductRequestDto;
import allib.platform.ecom.model.Cart;
import allib.platform.ecom.service.cart.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @MockBean
    private CartService cartService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        ProductRequestDto productRequest = new ProductRequestDto(1L, 10);
        Cart cart = new Cart(1L, false, null, new ArrayList<>());

        when(cartService.create(any(ProductRequestDto.class))).thenReturn(cart);

        mockMvc.perform(post("/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void addProduct() {
    }

    @Test
    void removeProduct() {
    }

    @Test
    void addCustomer() {
    }

    @Test
    void removeCustomer() throws Exception {
        Cart cart = new Cart(1L, false, null, new ArrayList<>());

        when(cartService.removeCustomer(anyLong())).thenReturn(cart);

        mockMvc.perform(delete("/carts/{cartId}/customer", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.customer", is((Object) null)));
    }

    @Test
    void changeCheckoutState() throws Exception {
        CheckoutStateRequestDto checkoutStateRequest = new CheckoutStateRequestDto(true);
        Cart cart = new Cart();
        cart.setReadyToCheckout(true);

        when(cartService.changeCheckoutState(1L, checkoutStateRequest)).thenReturn(cart);

        mockMvc.perform(patch("/carts/{cartId}/checkout", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checkoutStateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.readyToCheckout", is(true)));
    }
}