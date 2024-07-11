package sber.spring.Rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sber.spring.Rest.entities.Bin;
import sber.spring.Rest.entities.Client;
import sber.spring.Rest.service.BinService;
import sber.spring.Rest.service.ClientService;

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
        if (savedId == 0){
            return new ResponseEntity<String>("Client with such login already exists", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Created client with id: " + savedId, HttpStatus.CREATED);
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
    public ResponseEntity<String> clientDelete(@PathVariable int id){
        return clientService.deleteClientFromRep(id)
                ? new ResponseEntity<String>("Client successfully deleted", HttpStatus.NO_CONTENT)
                : new ResponseEntity<String>("Client with id = " + id + " doesn't exists", HttpStatus.NOT_FOUND);
    }
}
