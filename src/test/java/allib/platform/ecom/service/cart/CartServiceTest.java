package allib.platform.ecom.service.cart;

import allib.platform.ecom.dto.CheckoutStateRequestDto;
import allib.platform.ecom.dto.CustomerRequestDto;
import allib.platform.ecom.dto.ProductRequestDto;
import allib.platform.ecom.model.Cart;
import allib.platform.ecom.model.Customer;
import allib.platform.ecom.model.Product;
import allib.platform.ecom.repository.CartRepository;
import allib.platform.ecom.repository.CustomerRepository;
import allib.platform.ecom.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartServiceTest {

    @InjectMocks
    CartService cartService;

    @Mock
    CartRepository cartRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CustomerRepository customerRepository;

    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void create() {
        ProductRequestDto productRequest = mock(ProductRequestDto.class);
        Product product = mock(Product.class);
        Cart cart = new Cart();
        cart.addProduct(product, 10);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart createdCart = cartService.create(productRequest);

        assertNotNull(createdCart);
        assertEquals(1, createdCart.getProducts().size());
    }

    @Test
    void create_Exception() {
        Product product = mock(Product.class);

        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(product));

        assertThrows(RuntimeException.class, () -> {
            cartService.create(new ProductRequestDto(2L, 10));
        });
    }

    @Test
    void addProduct() {
    }

    @Test
    void removeProduct() {
    }

    @Test
    void addCustomer_CustomerFoundInDatabase() {
        CustomerRequestDto customerRequest = new CustomerRequestDto("John", "Doe", "jonh.doe@email.com");
        Cart cart = new Cart();
        Customer customer = new Customer(1L, "John", "Doe", "jonh.doe@email.com");

        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
        when(customerRepository.findByEmail(customerRequest.getEmail())).thenReturn(Optional.of(customer));
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart updatedCart = cartService.addCustomer(1L, customerRequest);

        assertEquals(cart.getCustomer().getEmail(), customer.getEmail());
    }

    @Test
    void addCustomer_CustomerNotFoundInTheDatabase() {
        CustomerRequestDto customerRequest = new CustomerRequestDto("John", "Doe", "jonh.doe@email.com");
        Cart cart = new Cart();
        Customer customer = new Customer(1L, "John", "Doe", "jonh.doe@email.com");

        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
        when(customerRepository.findByEmail(customerRequest.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart updatedCart = cartService.addCustomer(1L, customerRequest);

        assertEquals(cart.getCustomer().getEmail(), customer.getEmail());
    }

    @Test
    void removeCustomer() {
        Cart cart = new Cart();

        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.removeCustomer(1L);

        assertNull(updatedCart.getCustomer());
    }

    @Test
    void changeCheckoutState() {
        Cart cart = new Cart();
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.changeCheckoutState(1L, new CheckoutStateRequestDto(true));

        assertTrue(updatedCart.isReadyToCheckout());
    }

}