package allib.platform.ecom.service.cart;

import allib.platform.ecom.dto.CheckoutStateRequestDto;
import allib.platform.ecom.dto.CustomerRequestDto;
import allib.platform.ecom.dto.ProductRequestDto;
import allib.platform.ecom.model.Cart;

public interface ICartService {

    Cart create(ProductRequestDto productRequest);

    Cart addProduct(Long cartId, ProductRequestDto productRequest);

    Cart removeProduct(Long cartId, Long productId);

    // Add customer
    Cart addCustomer(Long cartId, CustomerRequestDto customerRequest);

    // Remove customer
    Cart removeCustomer(Long cartId);

    Cart changeCheckoutState(Long cartId, CheckoutStateRequestDto checkoutStateRequest);

}
