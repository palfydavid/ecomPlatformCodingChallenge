package allib.platform.ecom.controller.cart;

import allib.platform.ecom.dto.ProductRequestDto;
import allib.platform.ecom.model.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ICartController {

    @PostMapping("carts")
    ResponseEntity<Cart> create(@RequestBody ProductRequestDto productRequest);

}
