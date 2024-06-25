package com.autoBrokers.notification_service.controller;
import com.autoBrokers.notification_service.entities.Notification;
import com.autoBrokers.notification_service.model.Contract;
import com.autoBrokers.notification_service.service.impl.NotificationServiceImpl;
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
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1//notifications")
public class NotificationController {

    private final NotificationServiceImpl notificationService;

    public NotificationController(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    @Operation(summary = "Save Notification", description = "Method to save a notification")
    @ApiResponse(responseCode = "201", description = "Notification saved",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Notification.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> saveNotification(@Valid @RequestBody Notification notification) {
        try {
            Notification savedNotification = notificationService.save(notification);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNotification);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete Notification", description = "Method to delete a notification by ID")
    @ApiResponse(responseCode = "204", description = "Notification deleted")
    @ApiResponse(responseCode = "404", description = "Notification not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable("id") Long id) {
        try {
            notificationService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Notification by ID", description = "Method to get a notification by ID")
    @ApiResponse(responseCode = "200", description = "Notification found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Notification.class))})
    @ApiResponse(responseCode = "404", description = "Notification not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable("id") Long id) {
        try {
            Optional<Notification> notification = notificationService.getById(id);
            return notification.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get All Notifications", description = "Method to get all notifications")
    @ApiResponse(responseCode = "200", description = "Notifications found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Notification.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Notification>> getAllNotifications() {
        try {
            List<Notification> notifications = notificationService.getAll();
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Save Notification Contract", description = "Method to save a contract for a notification")
    @ApiResponse(responseCode = "201", description = "Contract saved",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Notification.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(value = "/{notificationId}/contracts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contract> saveNotificationContract(@PathVariable("notificationId") Long notificationId, @Valid @RequestBody Contract contract) {
        try {
            Contract savedContract = notificationService.saveContract(notificationId, contract);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedContract);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Notification and Contract", description = "Method to get a notification and their contracts")
    @ApiResponse(responseCode = "200", description = "Notification and contracts found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Notification.class))})
    @ApiResponse(responseCode = "404", description = "Notification not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/{notificationId}/contracts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getNotificationAndContracts(@PathVariable("notificationId") Long notificationId) {
        try {
            Map<String, Object> notificationAndContracts = notificationService.getNotificationtAndContract(notificationId);
            if (notificationAndContracts.containsKey("Mensaje")) {
                return new ResponseEntity<>(notificationAndContracts, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(notificationAndContracts, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }











}
