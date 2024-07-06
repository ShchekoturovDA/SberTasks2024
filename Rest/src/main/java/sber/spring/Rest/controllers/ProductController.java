package sber.spring.Rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sber.spring.Rest.entities.Product;
import sber.spring.Rest.service.ClientService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Void> productCreate(@RequestBody Product product) throws URISyntaxException {
        return ResponseEntity.created(new URI("http://localhost:8080/clients/" + clientService.saveProduct(product))).build();
    }

    @PutMapping()
    public void productUpdate(@RequestBody Product product){
        clientService.updateProduct(product);
    }

    @GetMapping({"/id"})
    public ResponseEntity<Product> productGet(@PathVariable long id){
        Optional<Product> searched = clientService.searchProductRep(id);
        return searched.isPresent()
                ? ResponseEntity.ok().body(searched.get())
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> productDelete(@PathVariable long id){
        return clientService.deleteProductFromRep(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
