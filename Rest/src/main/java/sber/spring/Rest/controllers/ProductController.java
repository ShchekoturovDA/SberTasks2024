package sber.spring.Rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sber.spring.Rest.entities.Product;
import sber.spring.Rest.service.ClientService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<String> productCreate(@RequestBody Product product) throws URISyntaxException {
        return new ResponseEntity<String>("Created Product with id: " + clientService.saveProduct(product), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<String> productUpdate(@RequestBody Product product){
        if (clientService.searchProductRep(product.getId()).isPresent()) {
            clientService.updateProduct(product);
            return new ResponseEntity<String>("Product with id = " + product.getId() + " successfully changed", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Product with id = " + product.getId() + " doesn't exists",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> productGet(@PathVariable int id){
        Optional<Product> searched = clientService.searchProductRep(id);
        return searched.isPresent()
                ? ResponseEntity.ok().body(searched.get())
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/filter/{name}")
    public ResponseEntity<List<Product>> productGetByName(@PathVariable String name){
        List<Product> searched = clientService.searchProductByNameRep(name);
        return !searched.isEmpty()
                ? ResponseEntity.ok().body(searched)
                : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> productDelete(@PathVariable int id){
        return clientService.deleteProductFromRep(id)
                ? new ResponseEntity<String>("Product with id = " + id + " successfully deleted", HttpStatus.NO_CONTENT)
                : new ResponseEntity<String>("Product with id = " + id + " doesn't exists", HttpStatus.NOT_FOUND);
    }

}
