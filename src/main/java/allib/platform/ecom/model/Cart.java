package allib.platform.ecom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isReadyToCheckout = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /*
        At this point, it seemed faster to just use @JsonIgnore to hide the products in API responses
        It definitely would be much better to create a cartDTO and return that with an organized list of products
     */
    @JsonIgnore
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProduct> products = new ArrayList<>();

    public void addProduct(Product product, int quantity) {
        CartProduct cartProduct = new CartProduct(this, product, quantity);
        products.add(cartProduct);
    }

    public void removeProduct(Product product) {
        products.removeIf(cartProduct -> cartProduct.getCart().equals(this) && cartProduct.getProduct().equals(product));
    }

}
