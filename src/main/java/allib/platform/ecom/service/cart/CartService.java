package allib.platform.ecom.service.cart;

import allib.platform.ecom.aspect.CheckCartCheckoutState;
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
    @CheckCartCheckoutState
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
    @CheckCartCheckoutState
    public Cart removeProduct(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new ResourceNotFoundException("Cart not found with the given ID " + cartId + ".")
        );
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with the given ID: " + productId + ".")
        );
        cart.removeProduct(product);
        // Here I can imagine deleting the cart when the last product is removed from it
        return cartRepository.save(cart);
    }

    /*
        Curretnly we can add a customer to a cart, but not cart to customer. It could be implemented in the
        customer service.
     */
    @Override
    @CheckCartCheckoutState
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
    @CheckCartCheckoutState
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

    /*
        MERGING TWO CARTS - how I would have solved it

        I would have created a POST method, since we create a new resource at the end of the day,
        and the URI would be /carts/{cartOneId}/merge/{cartTwoId}

        Steps in brief
            - check if both carts exist in the database
            - create a new cart object and copy the products from cart1
            - loop through the products of cart2
                - if product[i] is not in cart1, then copy to the new cart
                - if product[i] is also in cart1, then add the two quantities in the new cart
            - I would not check if customer has been added to cart1 earlier,
                just copy the current value (null or a customer id) to the new cart object
            - save the new cart to the database
            - the old carts could be either deleted or mark them as invalid or merged and probably store the new cart id
            - return the new cart
     */
}
