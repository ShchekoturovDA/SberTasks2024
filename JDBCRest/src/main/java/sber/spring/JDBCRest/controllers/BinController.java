package sber.spring.JDBCRest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sber.spring.JDBCRest.service.ClientService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("bin")
public class BinController {
    @Autowired
    private ClientService clientService;

    @PutMapping("/{binId}/add/{productId}")
    public ResponseEntity<Void> binAdd(@PathVariable int binId, @PathVariable int productId) throws URISyntaxException {
        if (clientService.isInBin(binId, productId)){
            return ResponseEntity.notFound().build();
        } else {
            int savedId = clientService.addToBin(binId, productId);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("{binId}/change/{productId}/{quantity}")
    public ResponseEntity<Void> binChangeQuantity(@PathVariable int binId, @PathVariable int productId, @PathVariable int quantity){
        if (!clientService.isInBin(binId, productId)) {
            return ResponseEntity.notFound().build();
        } else {
            clientService.changeQuantity(binId, productId, quantity);
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/{binId}/change/{productId}")
    public ResponseEntity<Void> deleteFromBin(@PathVariable int binId, @PathVariable int productId){
       if (!clientService.isInBin(binId, productId)) {
            return ResponseEntity.notFound().build();
        } else {
            return clientService.deleteProductFromBin(binId, productId)
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{binId}/payment")
    public ResponseEntity<Void> payment(@PathVariable int binId){
        if (clientService.searchBinRep(binId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return clientService.pay(binId)
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.notFound().build();
        }
    }
}
