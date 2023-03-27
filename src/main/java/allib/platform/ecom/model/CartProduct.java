package allib.platform.ecom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProduct {

    @EmbeddedId
    private CartProductKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cartId")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;

    private int quantity;

    public CartProduct(Cart cart, Product product, int quantity) {
        this.id = new CartProductKey(cart.getId(), product.getId());
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

}
