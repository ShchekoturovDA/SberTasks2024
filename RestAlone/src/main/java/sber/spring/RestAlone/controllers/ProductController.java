package sber.spring.RestAlone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sber.spring.RestAlone.entities.Product;
import sber.spring.RestAlone.service.ProductService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<String> productCreate(@RequestBody Product product) throws URISyntaxException {
        return ResponseEntity.created(new URI("http://localhost:8080/product/" + productService.saveProduct(product))).build();
    }

    @PutMapping()
    public void productUpdate(@RequestBody Product product) {
        productService.updateProduct(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> productGet(@PathVariable long id) {
        Optional<Product> searched = productService.searchProductRep(id);
        return searched.isPresent()
                ? ResponseEntity.ok().body(searched.get())
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/filter/{name}")
    public ResponseEntity<List<Product>> productGetByName(@PathVariable String name) {
        List<Product> searched = productService.searchProductByNameRep(name);
        return !searched.isEmpty()
                ? ResponseEntity.ok().body(searched)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> productDelete(@PathVariable long id) {
        return productService.deleteProductFromRep(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
