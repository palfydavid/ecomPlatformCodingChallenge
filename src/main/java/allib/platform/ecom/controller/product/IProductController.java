package allib.platform.ecom.controller.product;

import allib.platform.ecom.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface IProductController {

    @GetMapping("/products")
    ResponseEntity<List<Product>> getAll();

}
