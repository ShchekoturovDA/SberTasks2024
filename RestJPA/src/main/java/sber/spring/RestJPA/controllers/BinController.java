package sber.spring.RestJPA.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sber.spring.RestJPA.services.BinService;
import sber.spring.RestJPA.services.ClientService;
import sber.spring.RestJPA.services.ProductService;

import java.net.URISyntaxException;

@RestController
@RequestMapping("bin")
public class BinController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private BinService binService;
    @Autowired
    private ProductService productService;

/*
    @PutMapping("/{binId}/add/{productId}")
    public ResponseEntity<Void> binAdd(@PathVariable int binId, @PathVariable int productId) throws URISyntaxException {
        if (!binService.searchBinRep(binId).isPresent() || !productService.searchProductRep(productId).isPresent()) {
            return ResponseEntity.notFound().build();
        } else if (binService.isInBin(binId, productId)) {
            return ResponseEntity.notFound().build();
        } else {
            int savedId = binService.addToBin(binId, productId);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("{binId}/change/{productId}/{quantity}")
    public ResponseEntity<Void> binChangeQuantity(@PathVariable int binId, @PathVariable int productId, @PathVariable int quantity) {
        if (!binService.searchBinRep(binId).isPresent() || !productService.searchProductRep(productId).isPresent()) {
            return ResponseEntity.notFound().build();
        } else if (!binService.isInBin(binId, productId)) {
            return ResponseEntity.notFound().build();
        } else {
            binService.changeQuantity(binId, productId, quantity);
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/{binId}/change/{productId}")
    public ResponseEntity<Void> deleteFromBin(@PathVariable int binId, @PathVariable int productId) {
        if (!binService.searchBinRep(binId).isPresent() || !productService.searchProductRep(productId).isPresent()) {
            return ResponseEntity.notFound().build();
        } else if (!binService.isInBin(binId, productId)) {
            return ResponseEntity.notFound().build();
        } else {
            return binService.deleteProductFromBin(binId, productId)
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{binId}/payment")
    public ResponseEntity<Void> payment(@PathVariable int binId) {
        if (!binService.searchBinRep(binId).isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            return binService.pay(binId)
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.notFound().build();
        }
    }*/
}
