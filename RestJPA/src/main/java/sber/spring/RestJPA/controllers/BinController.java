package sber.spring.RestJPA.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sber.spring.RestJPA.entities.Bin;
import sber.spring.RestJPA.entities.Product;
import sber.spring.RestJPA.entities.ProductBin;
import sber.spring.RestJPA.services.BinService;
import sber.spring.RestJPA.services.ClientService;
import sber.spring.RestJPA.services.ProductService;

import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("bin")
public class BinController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private BinService binService;
    @Autowired
    private ProductService productService;


    @PutMapping("/{binId}/add/{productId}")
    public ResponseEntity<Void> binAdd(@PathVariable int binId, @PathVariable int productId) throws URISyntaxException {
        Optional<Bin> bin = binService.findById(binId);
        Optional<Product> product = productService.getById(binId);
        if (!bin.isPresent() || !product.isPresent()) {
            return ResponseEntity.notFound().build();
        } else if (binService.isInBin(binId, productId)) {
            return ResponseEntity.notFound().build();
        } else {
            ProductBin productBin = new ProductBin();
            productBin.setBinId(bin.get());
            productBin.setProduct(product.get());
            productBin.setQuantity(1);
            binService.saveProduct(productBin);
            return ResponseEntity.ok().build();
        }
    }


    @PutMapping("{binId}/change/{productId}/{quantity}")
    public ResponseEntity<Void> binChangeQuantity(@PathVariable int binId, @PathVariable int productId, @PathVariable int quantity) {
        if (!binService.findById(binId).isPresent() || !productService.getById(binId).isPresent()) {
            return ResponseEntity.notFound().build();
        } else if (!binService.isInBin(binId, productId)) {
            return ResponseEntity.notFound().build();
        } else {
            ProductBin productBin = binService.findProductBin(binId, productId).get();
            productBin.setQuantity(quantity);
            binService.saveProduct(productBin);
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/{binId}/change/{productId}")
    public ResponseEntity<Void> deleteFromBin(@PathVariable int binId, @PathVariable int productId) {
        if (!binService.findById(binId).isPresent() || !productService.getById(binId).isPresent()) {
            return ResponseEntity.notFound().build();
        } else if (!binService.isInBin(binId, productId)) {
            return ResponseEntity.notFound().build();
        } else {
            binService.deleteProduct(binId, productId);
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{binId}/payment")
    public ResponseEntity<Void> payment(@PathVariable int binId) {
        if (!binService.findById(binId).isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            binService.pay(binId);
            ResponseEntity.noContent().build()ж
        }
    }
}
