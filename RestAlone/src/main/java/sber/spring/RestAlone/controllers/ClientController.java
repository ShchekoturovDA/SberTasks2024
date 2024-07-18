package sber.spring.RestAlone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sber.spring.RestAlone.entities.Client;
import sber.spring.RestAlone.service.BinService;
import sber.spring.RestAlone.service.ClientService;

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
        long savedId = clientService.saveClient(client, binService.createBin());
        if (savedId == 0) {
            return new ResponseEntity<String>("Client with such login already exists", HttpStatus.OK);
        } else {
            return ResponseEntity.created(new URI("http://localhost:8080/client/" + savedId)).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> clientGet(@PathVariable long id) {
        Optional<Client> searched = clientService.searchClientRep(id);
        return searched.isPresent()
                ? ResponseEntity.ok().body(searched.get())
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> clientDelete(@PathVariable long id) {
        return clientService.deleteClientFromRep(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
