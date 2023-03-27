package allib.platform.ecom.controller.product;

import allib.platform.ecom.model.Product;
import allib.platform.ecom.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController implements IProductController {

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<List<Product>>(productService.getAll(), HttpStatus.OK);
    }
}
