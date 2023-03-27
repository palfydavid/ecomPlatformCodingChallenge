package allib.platform.ecom.controller.cart;

import allib.platform.ecom.dto.CheckoutStateRequestDto;
import allib.platform.ecom.dto.CustomerRequestDto;
import allib.platform.ecom.dto.ProductRequestDto;
import allib.platform.ecom.model.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ICartController {

    @PostMapping("/carts")
    ResponseEntity<Cart> create(@RequestBody ProductRequestDto productRequest);

    @PostMapping("/carts/{cartId}/products")
    ResponseEntity<Cart> addProduct(@PathVariable Long cartId, @RequestBody ProductRequestDto productRequest);

    @DeleteMapping("/carts/{cartId}/products/{productId}")
    ResponseEntity<Cart> removeProduct(@PathVariable Long cartId, @PathVariable Long productId);

    @PostMapping("/carts/{cartId}/customer")
    ResponseEntity<Cart> addCustomer(@PathVariable Long cartId, @RequestBody CustomerRequestDto customerRequest);

    @DeleteMapping("/carts/{cartId}/customer")
    ResponseEntity<Cart> removeCustomer(@PathVariable Long cartId);

    @PatchMapping("/carts/{cartId}/checkout")
    ResponseEntity<Cart> changeCheckoutState(@PathVariable Long cartId, @RequestBody CheckoutStateRequestDto checkoutStateRequest);

}
