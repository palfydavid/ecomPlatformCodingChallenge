package allib.platform.ecom.service.cart;

import allib.platform.ecom.dto.CheckoutStateRequestDto;
import allib.platform.ecom.dto.CustomerRequestDto;
import allib.platform.ecom.dto.ProductRequestDto;
import allib.platform.ecom.exception.ResourceNotFoundException;
import allib.platform.ecom.model.Cart;
import allib.platform.ecom.model.Customer;
import allib.platform.ecom.model.Product;
import allib.platform.ecom.repository.CartRepository;
import allib.platform.ecom.repository.CustomerRepository;
import allib.platform.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Cart create(ProductRequestDto productRequest) {
        Product product = productRepository.findById(productRequest.getProductId()).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with the given ID: " + productRequest.getProductId() + ".")
        );
        Cart cart = new Cart();
        cart.addProduct(product, productRequest.getQuantity());
        return cartRepository.save(cart);
    }

    @Override
    public Cart addProduct(Long cartId, ProductRequestDto productRequest) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new ResourceNotFoundException("Cart not found with the given ID " + cartId + ".")
        );
        Product product = productRepository.findById(productRequest.getProductId()).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with the given ID: " + productRequest.getProductId() + ".")
        );
        cart.addProduct(product, productRequest.getQuantity());
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProduct(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new ResourceNotFoundException("Cart not found with the given ID " + cartId + ".")
        );
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with the given ID: " + productId + ".")
        );
        cart.removeProduct(product);
        // Cart might be deleted when the user removed the last product
        return cartRepository.save(cart);
    }

    @Override
    public Cart addCustomer(Long cartId, CustomerRequestDto customerRequest) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new ResourceNotFoundException("Cart not found with the given ID: " + cartId + ".")
        );
        Optional<Customer> customer = customerRepository.findByEmail(customerRequest.getEmail());
        if(customer.isPresent()) {
            cart.setCustomer(customer.get());
        } else {
            Customer newCustomer = new Customer();
            newCustomer.setFirstName(customerRequest.getFirstName());
            newCustomer.setLastName(customerRequest.getLastName());
            newCustomer.setEmail(customerRequest.getEmail());
            newCustomer = customerRepository.save(newCustomer);
            cart.setCustomer(newCustomer);
        }
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeCustomer(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new ResourceNotFoundException("Cart not found with the given ID: " + cartId + ".")
        );
        cart.setCustomer(null);
        return cartRepository.save(cart);
    }

    @Override
    public Cart changeCheckoutState(Long cartId, CheckoutStateRequestDto checkoutStateRequest) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new ResourceNotFoundException("Cart not found with the given ID: " + cartId + ".")
        );
        cart.setReadyToCheckout(checkoutStateRequest.isReadyToCheckout());
        return cartRepository.save(cart);
    }
}
