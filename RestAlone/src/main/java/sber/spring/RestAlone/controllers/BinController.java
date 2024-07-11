package sber.spring.RestAlone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sber.spring.RestAlone.service.BinService;
import sber.spring.RestAlone.service.ClientService;
import sber.spring.RestAlone.service.ProductService;

@RestController
@RequestMapping("bin")
public class BinController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private BinService binService;

    @PutMapping("/{binId}/add/{productId}")
    public ResponseEntity<Void> binAdd(@PathVariable long binId, @PathVariable long productId) {
        if (binService.searchBinRep(binId).isPresent() || productService.searchProductRep(productId).isPresent()) {
            return ResponseEntity.notFound().build();
        } else if (!binService.isInBin(binId, productId)) {
            binService.addToBin(binId, productService.searchProductRep(productId).get());
            clientService.updateBin(binId, binService.searchBinRep(binId).get());
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("{binId}/change/{productId}/{quantity}")
    public ResponseEntity<Void> binChangeQuantity(@PathVariable long binId, @PathVariable long productId, @PathVariable int quantity) {
        if (binService.searchBinRep(binId).isEmpty() || productService.searchProductRep(productId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (!binService.isInBin(binId, productId)) {
            return ResponseEntity.notFound().build();
        } else {
            binService.changeQuantity(binId, productId, quantity);
            clientService.updateBin(binId, binService.searchBinRep(binId).get());
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/{binId}/change/{productId}")
    public ResponseEntity<Void> deleteFromBin(@PathVariable long binId, @PathVariable long productId) {
        if (binService.searchBinRep(binId).isEmpty() || productService.searchProductRep(productId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (!binService.isInBin(binId, productId)) {
            return ResponseEntity.notFound().build();
        } else {
            binService.deleteProductFromBin(binId, productId);
            clientService.updateBin(binId, binService.searchBinRep(binId).get());
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/{binId}/payment")
    public ResponseEntity<Void> payment(@PathVariable long binId) {
        if (binService.searchBinRep(binId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            binService.pay(binId);
            clientService.updateBin(binId, binService.searchBinRep(binId).get());
            return ResponseEntity.ok().build();
        }
    }
}
