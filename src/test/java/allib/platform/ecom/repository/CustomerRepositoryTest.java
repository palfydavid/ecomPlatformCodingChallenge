package allib.platform.ecom.repository;

import allib.platform.ecom.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(1L, "John", "Doe", "john.doe@email.com");
        customerRepository.save(customer);
    }

    @AfterEach
    void tearDown() {
        customer = null;
        customerRepository.deleteAll();
    }

    @Test
    void findByEmail_Found() {
        Optional<Customer> customerTest = customerRepository.findByEmail("john.doe@email.com");
        assertTrue(customerTest.isPresent());
        assertEquals(customer, customerTest.get());
    }

    @Test
    void findByEmail_NotFound() {
        Optional<Customer> customerTest = customerRepository.findByEmail("will.smith@email.com");
        assertFalse(customerTest.isPresent());
    }

}