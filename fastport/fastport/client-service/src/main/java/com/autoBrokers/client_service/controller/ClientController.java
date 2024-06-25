package com.autoBrokers.client_service.controller;

import com.autoBrokers.client_service.entities.Client;
import com.autoBrokers.client_service.model.Card;
import com.autoBrokers.client_service.model.Comment;
import com.autoBrokers.client_service.model.Contract;
import com.autoBrokers.client_service.service.IClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clients")
@Tag(name="Client", description="Web Service RESTful of Clients")
public class ClientController {
    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "List of Clients", description = "Method to list all clients")
    @ApiResponse(responseCode = "200", description = "Clients found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "204", description = "Clients not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Client>> getAllClients() {
        try {
            List<Client> clients = clientService.getAll();
            if (!clients.isEmpty()) {
                return new ResponseEntity<>(clients, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Client by Id", description = "Method to find a client by id")
    @ApiResponse(responseCode = "200", description = "Client found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "404", description = "Client not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> findClientById(@PathVariable("id") Long id) {
        try {
            Optional<Client> client = clientService.getById(id);
            if (client.isPresent())
                return new ResponseEntity<>(client.get(), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Insert Client", description = "Method to insert a client")
    @ApiResponse(responseCode = "201", description = "Client created",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> insertClient(@Valid @RequestBody Client client) {
        try {
            Client newClient = clientService.save(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update Client", description = "Method to update a client")
    @ApiResponse(responseCode = "200", description = "Client updated",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "404", description = "Client not updated")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> updateClient(@PathVariable("id") Long id,
                                               @Valid @RequestBody Client client) {
        try {
            Optional<Client> clientUpdate = clientService.getById(id);
            if (!clientUpdate.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            client.setId(id);
            clientService.save(client);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete Client", description = "Method to delete a client")
    @ApiResponse(responseCode = "200", description = "Client deleted")
    @ApiResponse(responseCode = "404", description = "Client not deleted")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {
        try {
            Optional<Client> clientDelete = clientService.getById(id);
            if (!clientDelete.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            clientService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Client by Email and Password", description = "Method to find a client by email and password")
    @ApiResponse(responseCode = "200", description = "Client found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "404", description = "Client not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/searchEmailPassword/{email}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> findClientByEmailAndPassword(
            @PathVariable("email") String email,
            @PathVariable("password") String password) {
        try {
            Client client = clientService.findByEmailAndPassword(email, password);
            if (client == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Save Client Comment", description = "Method to save a comment for a client")
    @ApiResponse(responseCode = "201", description = "Comment saved",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(value = "/{clientId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> saveClientComment(@PathVariable("clientId") Long clientId, @Valid @RequestBody Comment comment) {
        try {
            Comment savedComment = clientService.saveComment(clientId, comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Client and Comments", description = "Method to get a client and their comments")
    @ApiResponse(responseCode = "200", description = "Client and comments found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "404", description = "Client not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/{clientId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getClientAndComments(@PathVariable("clientId") Long clientId) {
        try {
            Map<String, Object> clientAndComments = clientService.getClientAndComment(clientId);
            if (clientAndComments.containsKey("Mensaje")) {
                return new ResponseEntity<>(clientAndComments, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(clientAndComments, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Save Client Contract", description = "Method to save a contract for a client")
    @ApiResponse(responseCode = "201", description = "Contract saved",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(value = "/{clientId}/contracts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contract> saveClientContract(@PathVariable("clientId") Long clientId, @Valid @RequestBody Contract contract) {
        try {
            Contract savedContract = clientService.saveContract(clientId, contract);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedContract);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Client and Contract", description = "Method to get a client and their comments")
    @ApiResponse(responseCode = "200", description = "Client and comments found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "404", description = "Client not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/{clientId}/contracts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getClientAndContracts(@PathVariable("clientId") Long clientId) {
        try {
            Map<String, Object> clientAndContracts = clientService.getClientAndContract(clientId);
            if (clientAndContracts.containsKey("Mensaje")) {
                return new ResponseEntity<>(clientAndContracts, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(clientAndContracts, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Operation(summary = "Save Client Card", description = "Method to save a card for a client")
    @ApiResponse(responseCode = "201", description = "Card saved",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(value = "/{clientId}/cards", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Card> saveClientCard(@PathVariable("clientId") Long clientId, @Valid @RequestBody Card card) {
        try {
            Card savedCard = clientService.saveCard(clientId, card);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Client and Cards", description = "Method to get a client and their cards")
    @ApiResponse(responseCode = "200", description = "Client and cards found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "404", description = "Client not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/{clientId}/cards", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getClientAndCards(@PathVariable("clientId") Long clientId) {
        try {
            Map<String, Object> clientAndCards = clientService.getClientAndCard(clientId);
            if (clientAndCards.containsKey("Mensaje")) {
                return new ResponseEntity<>(clientAndCards, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(clientAndCards, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }








}
