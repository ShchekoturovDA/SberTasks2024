package sber.spring.RestJPA.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sber.spring.RestJPA.entities.Product;
import sber.spring.RestJPA.services.ProductService;

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
        return ResponseEntity.created(new URI("http://localhost:8080/product/" + productService.save(product).getId())).build();
    }

    @PutMapping()
    public void productUpdate(@RequestBody Product product) {
        if (productService.isExist(product.getId())) {
            productService.save(product);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> productGet(@PathVariable int id) {
        Optional<Product> searched = productService.getById(id);
        return searched.isPresent()
                ? ResponseEntity.ok().body(searched.get())
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/filter/{name}")
    public ResponseEntity<List<Product>> productGetByName(@PathVariable String name) {
        List<Product> searched = productService.findAllByName(name);
        return !searched.isEmpty()
                ? ResponseEntity.ok().body(searched)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> productDelete(@PathVariable int id) {
        if (productService.isExist(id)) {
            productService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
