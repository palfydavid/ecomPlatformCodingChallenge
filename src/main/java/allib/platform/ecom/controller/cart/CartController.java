package allib.platform.ecom.controller.cart;

import allib.platform.ecom.dto.ProductRequestDto;
import allib.platform.ecom.model.Cart;
import allib.platform.ecom.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController implements ICartController {

    @Autowired
    private CartService cartService;

    @Override
    public ResponseEntity<Cart> create(ProductRequestDto productRequest) {
        return new ResponseEntity<Cart>(cartService.create(productRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Cart> addProduct(Long cartId, ProductRequestDto productRequest) {
        return new ResponseEntity<Cart>(cartService.addProduct(cartId, productRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Cart> removeProduct(Long cartId, Long productId) {
        return new ResponseEntity<Cart>(cartService.removeProduct(cartId, productId), HttpStatus.OK);
    }

}
