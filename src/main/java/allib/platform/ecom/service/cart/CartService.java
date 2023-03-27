package allib.platform.ecom.service.cart;

import allib.platform.ecom.dto.ProductRequestDto;
import allib.platform.ecom.model.Cart;
import allib.platform.ecom.model.Product;
import allib.platform.ecom.repository.CartRepository;
import allib.platform.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart create(ProductRequestDto productRequest) {
        Product product = productRepository.findById(productRequest.getProductId()).orElseThrow();
        Cart cart = new Cart();
        cart.addProduct(product, productRequest.getQuantity());
        return cartRepository.save(cart);
    }

    @Override
    public Cart addProduct(Long cartId, ProductRequestDto productRequest) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        Product product = productRepository.findById(productRequest.getProductId()).orElseThrow();
        cart.addProduct(product, productRequest.getQuantity());
        return cartRepository.save(cart);
    }

}
