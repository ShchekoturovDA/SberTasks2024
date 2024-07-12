package sber.spring.RestJPA.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sber.spring.RestJPA.entities.Client;
import sber.spring.RestJPA.services.BinService;
import sber.spring.RestJPA.services.ClientService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private BinService binService;

    @PostMapping
    public ResponseEntity<String> clientReg(@RequestBody Client client) throws URISyntaxException {
        if (clientService.uniqueLogin(client)) {
            return new ResponseEntity<String>("Client with such login already exists", HttpStatus.OK);
        } else {
            client.setBin(binService.save());
            Client savedClient = clientService.save(client);
            return ResponseEntity.created(new URI("http://localhost:8080/client/" + savedClient.getId())).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> clientGet(@PathVariable int id) {
        Optional<Client> searched = clientService.findById(id);
        return searched.isPresent()
                ? ResponseEntity.ok().body(searched.get())
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> clientDelete(@PathVariable int id) {
        if (clientService.isExists(id)) {
            clientService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
