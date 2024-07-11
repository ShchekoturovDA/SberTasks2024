package sber.spring.Rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sber.spring.Rest.service.BinService;
import sber.spring.Rest.service.ClientService;
import sber.spring.Rest.service.ProductService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("bin")
public class BinController {
    @Autowired
    private BinService binService;
    @Autowired
    private ProductService productService;

    @PutMapping("/{binId}/add/{productId}")
    public ResponseEntity<String> binAdd(@PathVariable int binId, @PathVariable int productId) throws URISyntaxException {
        if(!productService.searchProductRep(productId).isPresent()){
            return new ResponseEntity<String>("Product with id = " + productId + " doesn't exists", HttpStatus.NOT_FOUND);
        } else if(!binService.searchBinRep(binId).isPresent()){
            return new ResponseEntity<String>("bin with id = " + binId + " doesn't exists", HttpStatus.NOT_FOUND);
        } else if (binService.isInBin(binId, productId)){
            return new ResponseEntity<String>("You already added this product", HttpStatus.NOT_FOUND);
        } else {
            binService.addToBin(binId, productId);
            return new ResponseEntity<String>("Product added", HttpStatus.CREATED);
        }
    }

    @PutMapping("{binId}/change/{productId}/{quantity}")
    public ResponseEntity<String> binChangeQuantity(@PathVariable int binId, @PathVariable int productId, @PathVariable int quantity){
        if(!productService.searchProductRep(productId).isPresent()){
            return new ResponseEntity<String>("Product with id = " + productId + " doesn't exists", HttpStatus.NOT_FOUND);
        } else if(!binService.searchBinRep(binId).isPresent()){
            return new ResponseEntity<String>("bin with id = " + binId + " doesn't exists", HttpStatus.NOT_FOUND);
        } else if (!binService.isInBin(binId, productId)) {
            return new ResponseEntity<String>("You didn't add this product", HttpStatus.NOT_FOUND);
        } else {
            binService.changeQuantity(binId, productId, quantity);
            return new ResponseEntity<String>("Quantity changed", HttpStatus.OK);
        }
    }

    @DeleteMapping("/{binId}/change/{productId}")
    public ResponseEntity<String> deleteFromBin(@PathVariable int binId, @PathVariable int productId){
        if(!productService.searchProductRep(productId).isPresent()){
            return new ResponseEntity<String>("Product with id = " + productId + " doesn't exists", HttpStatus.NOT_FOUND);
        } else if(!binService.searchBinRep(binId).isPresent()){
            return new ResponseEntity<String>("bin with id = " + binId + " doesn't exists", HttpStatus.NOT_FOUND);
        } else {
            return binService.deleteProductFromBin(binId, productId)
                    ? new ResponseEntity<String>("Product deleted successfully", HttpStatus.NO_CONTENT)
                    : new ResponseEntity<String>("You didn't add this product", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{binId}/payment")
    public ResponseEntity<Void> payment(@PathVariable int binId){
        if (binService.searchBinRep(binId).isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            return binService.pay(binId)
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.notFound().build();
        }
    }
}
