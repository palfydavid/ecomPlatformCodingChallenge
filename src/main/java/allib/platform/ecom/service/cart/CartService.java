package allib.platform.ecom.service.cart;

import allib.platform.ecom.model.Cart;
import allib.platform.ecom.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart create(Cart cart) {
        return null;
    }

}
