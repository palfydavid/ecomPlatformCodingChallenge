package allib.platform.ecom.service.cart;

import allib.platform.ecom.dto.CheckoutStateRequestDto;
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

    @Override
    public Cart removeProduct(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        cart.removeProduct(product);
        // Cart might be deleted when the user removed the last product
        return cartRepository.save(cart);
    }

    // Add customer

    // Remove customers

    @Override
    public Cart changeCheckoutState(Long cartId, CheckoutStateRequestDto checkoutStateRequest) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        cart.setReadyToCheckout(checkoutStateRequest.isReadyToCheckout());
        return cartRepository.save(cart);
    }
}
