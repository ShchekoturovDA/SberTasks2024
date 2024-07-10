package sber.spring.Rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> clientReg(@RequestBody Client client) throws URISyntaxException {
        long savedId = clientService.saveClient(client);
        if (savedId == 0){
            return new ResponseEntity<String>("Client with such login already exists", HttpStatus.OK);
        } else {
            return ResponseEntity.created(new URI("http://localhost:8080/client/" + savedId)).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> clientGet(@PathVariable int id){
        Optional<Client> searched = clientService.searchClientRep(id);
        return searched.isPresent()
                ? ResponseEntity.ok().body(searched.get())
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> clientDelete(@PathVariable int id){
        return clientService.deleteClientFromRep(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
