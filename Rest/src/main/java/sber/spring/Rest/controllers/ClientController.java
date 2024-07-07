package sber.spring.Rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sber.spring.Rest.entities.Client;
import sber.spring.Rest.service.ClientService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Void> clientReg(@RequestBody Client client) throws URISyntaxException {
        long savedId = clientService.saveClient(client);
        if (savedId == 0){
            return null;
        } else {
            return ResponseEntity.created(new URI("http://localhost:8080/clients/" + savedId)).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> clientGet(@PathVariable long id){
        Optional<Client> searched = clientService.searchClientRep(id);
        return searched.isPresent()
                ? ResponseEntity.ok().body(searched.get())
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> clientDelete(@PathVariable long id){
        return clientService.deleteClientFromRep(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{clientId}/bin/{productId}")
    public ResponseEntity<Void> binAdd(@PathVariable long clientId, @PathVariable long productId){
        if (clientService.searchClientRep(clientId).isEmpty() || clientService.searchProductRep(productId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (clientService.searchProductRep(productId).get().getQuantity() == 0) {
            return ResponseEntity.notFound().build();
        } else {
            if (!clientService.isInBin(clientId, productId)) {
                clientService.addToBin(clientId, productId);
                clientService.sellProduct(productId, 1);
            }
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/{clientId}/bin/{productId}/{quantity}")
    public ResponseEntity<Void> binChangeQuantity(@PathVariable long clientId, @PathVariable long productId, @PathVariable int quantity){
        if (clientService.searchClientRep(clientId).isEmpty() || clientService.searchProductRep(productId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (!clientService.isInBin(clientId, productId)) {
            return ResponseEntity.notFound().build();
        } else {
            clientService.changeQuantity(clientId, productId, quantity);
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/{clientId}/bin/{productId}")
    public ResponseEntity<Void> deleteFromBin(@PathVariable long clientId, @PathVariable long productId){
        if (clientService.searchClientRep(clientId).isEmpty() || clientService.searchProductRep(productId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (!clientService.isInBin(clientId, productId)) {
            return ResponseEntity.notFound().build();
        } else {
            clientService.deleteProductFromBin(clientId, productId);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/{clientId}/payment")
    public ResponseEntity<Void> payment(@PathVariable long clientId){
        if (clientService.searchClientRep(clientId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            clientService.pay(clientId);
            return ResponseEntity.ok().build();
        }
    }
}
