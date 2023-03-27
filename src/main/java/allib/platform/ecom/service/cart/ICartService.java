package allib.platform.ecom.service.cart;

import allib.platform.ecom.dto.ProductRequestDto;
import allib.platform.ecom.model.Cart;

public interface ICartService {

    Cart create(ProductRequestDto productRequest);

    Cart addProduct(Long cartId, ProductRequestDto productRequest);

}
