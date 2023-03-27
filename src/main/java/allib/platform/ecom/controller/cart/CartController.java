package allib.platform.ecom.controller.cart;

import allib.platform.ecom.dto.CheckoutStateRequestDto;
import allib.platform.ecom.dto.CustomerRequestDto;
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

    @Override
    public ResponseEntity<Cart> addCustomer(Long cartId, CustomerRequestDto customerRequest) {
        return new ResponseEntity<Cart>(cartService.addCustomer(cartId, customerRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Cart> removeCustomer(Long cartId) {
        return new ResponseEntity<Cart>(cartService.removeCustomer(cartId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Cart> changeCheckoutState(Long cartId, CheckoutStateRequestDto checkoutStateRequest) {
        return new ResponseEntity<Cart>(cartService.changeCheckoutState(cartId, checkoutStateRequest), HttpStatus.OK);
    }

}
