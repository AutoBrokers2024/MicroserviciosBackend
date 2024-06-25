package com.autoBrokers.payment_service.controller;

import com.autoBrokers.payment_service.entities.Card;
import com.autoBrokers.payment_service.service.ICardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    private final ICardService cardService;

    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @Operation(summary = "Save Card", description = "Method to save a card")
    @ApiResponse(responseCode = "201", description = "Card saved",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Card.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Card> saveCard(@Valid @RequestBody Card card) {
        try {
            Card savedCard = cardService.save(card);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete Card", description = "Method to delete a card by ID")
    @ApiResponse(responseCode = "204", description = "Card deleted")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable("id") Long id) {
        try {
            cardService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Card by ID", description = "Method to get a card by ID")
    @ApiResponse(responseCode = "200", description = "Card found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Card.class))})
    @ApiResponse(responseCode = "404", description = "Card not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable("id") Long id) {
        try {
            Optional<Card> card = cardService.getById(id);
            return card.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get All Cards", description = "Method to get all cards")
    @ApiResponse(responseCode = "200", description = "Cards found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Card.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Card>> getAllCards() {
        try {
            List<Card> cards = cardService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(cards);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Cards by Driver ID", description = "Method to get cards by driver ID")
    @ApiResponse(responseCode = "200", description = "Cards found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Card.class))})
    @ApiResponse(responseCode = "404", description = "Cards not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<Card>> getCardsByDriverId(@PathVariable("driverId") Long driverId) {
        try {
            List<Card> cards = cardService.findByDriverId(driverId);
            return cards.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.status(HttpStatus.OK).body(cards);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Cards by Client ID", description = "Method to get cards by client ID")
    @ApiResponse(responseCode = "200", description = "Cards found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Card.class))})
    @ApiResponse(responseCode = "404", description = "Cards not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Card>> getCardsByClientId(@PathVariable("clientId") Long clientId) {
        try {
            List<Card> cards = cardService.findByClientId(clientId);
            return cards.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.status(HttpStatus.OK).body(cards);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
