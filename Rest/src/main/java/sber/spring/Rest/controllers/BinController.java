package sber.spring.Rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sber.spring.Rest.service.BinService;
import sber.spring.Rest.service.ProductService;

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
        String message = "";
        if (!productService.searchProductRep(productId).isPresent()) {
            message = "Product with id = " + productId + " doesn't exists";
        } else if (!binService.searchBinRep(binId).isPresent()) {
            message = "bin with id = " + binId + " doesn't exists";
        } else if (binService.isInBin(binId, productId)) {
            message = "You already added this product";
        } else {
            binService.addToBin(binId, productId);
            return new ResponseEntity<String>("Product added", HttpStatus.CREATED);
        }
        return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);

    }

    @PutMapping("{binId}/change/{productId}/{quantity}")
    public ResponseEntity<String> binChangeQuantity(@PathVariable int binId, @PathVariable int productId, @PathVariable int quantity) {
        String message = "";
        if (!productService.searchProductRep(productId).isPresent()) {
            message = "Product with id = " + productId + " doesn't exists";
        } else if (!binService.searchBinRep(binId).isPresent()) {
            message = "bin with id = " + binId + " doesn't exists";
        } else if (!binService.isInBin(binId, productId)) {
            message = "You didn't add this product";
        } else {
            binService.changeQuantity(binId, productId, quantity);
            return new ResponseEntity<String>("Quantity changed", HttpStatus.OK);
        }
        return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{binId}/change/{productId}")
    public ResponseEntity<String> deleteFromBin(@PathVariable int binId, @PathVariable int productId) {
        String message = "";
        if (!productService.searchProductRep(productId).isPresent()) {
            message = "Product with id = " + productId + " doesn't exists";
        } else if (!binService.searchBinRep(binId).isPresent()) {
            message = "bin with id = " + binId + " doesn't exists";
        } else if(!binService.deleteProductFromBin(binId, productId)){
            message = "You didn't add this product";
        } else {
            return new ResponseEntity<String>("Product deleted successfully", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{binId}/payment")
    public ResponseEntity<Void> payment(@PathVariable int binId) {
        if (!binService.searchBinRep(binId).isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            binService.pay(binId);
            return ResponseEntity.noContent().build();
        }
    }
}
