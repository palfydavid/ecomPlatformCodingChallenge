package allib.platform.ecom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    /*
        In this project I tried to solve everything from cart perspective,
        but if we wanted to get all the carts of a customer then we should
        set up a bidirectional relationship
     */
//    @OneToMany(mappedBy = "customer")
//    private List<Cart> carts = new ArrayList<>();

}
