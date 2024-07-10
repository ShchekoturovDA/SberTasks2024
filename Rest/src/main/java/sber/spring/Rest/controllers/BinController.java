package sber.spring.Rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sber.spring.Rest.service.ClientService;

@RestController
@RequestMapping("bin")
public class BinController {
    @Autowired
    private ClientService clientService;

    @PutMapping("/{binId}/add/{productId}")
    public ResponseEntity<Void> binAdd(@PathVariable int binId, @PathVariable int productId){
        if (clientService.searchBinRep(binId).isEmpty() || clientService.searchProductRep(productId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (clientService.searchProductRep(productId).get().getQuantity() == 0) {
            return ResponseEntity.notFound().build();
        } else if (!clientService.isInBin(binId, productId)) {
            clientService.addToBin(binId, productId);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("{binId}/change/{productId}/{quantity}")
    public ResponseEntity<Void> binChangeQuantity(@PathVariable int binId, @PathVariable int productId, @PathVariable int quantity){
        if (clientService.searchBinRep(binId).isEmpty() || clientService.searchProductRep(productId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (!clientService.isInBin(binId, productId)) {
            return ResponseEntity.notFound().build();
        } else {
            clientService.changeQuantity(binId, productId, quantity);
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/{binId}/change/{productId}")
    public ResponseEntity<Void> deleteFromBin(@PathVariable int binId, @PathVariable int productId){
        if (clientService.searchBinRep(binId).isEmpty() || clientService.searchProductRep(productId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (!clientService.isInBin(binId, productId)) {
            return ResponseEntity.notFound().build();
        } else {
            clientService.deleteProductFromBin(binId, productId);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/{binId}/payment")
    public ResponseEntity<Void> payment(@PathVariable int binId){
        if (clientService.searchBinRep(binId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            clientService.pay(binId);
            return ResponseEntity.ok().build();
        }
    }
}
