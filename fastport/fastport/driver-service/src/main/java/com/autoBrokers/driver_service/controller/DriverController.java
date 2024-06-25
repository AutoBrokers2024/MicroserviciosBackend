package com.autoBrokers.driver_service.controller;


import com.autoBrokers.driver_service.entities.Driver;
import com.autoBrokers.driver_service.model.Card;
import com.autoBrokers.driver_service.model.Comment;
import com.autoBrokers.driver_service.model.Experience;
import com.autoBrokers.driver_service.model.Vehicle;
import com.autoBrokers.driver_service.service.IDriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {
    private final IDriverService driverService;

    public DriverController(IDriverService driverService) {
        this.driverService = driverService;
    }

    // Retornar todos los drivers
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List of Drivers", description = "Method to list all drivers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Drivers found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))}),
            @ApiResponse(responseCode = "204", description = "Drivers not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Driver>> getAllDrivers() {
        try {
            List<Driver> drivers = driverService.getAll();
            if (!drivers.isEmpty()) {
                return new ResponseEntity<>(drivers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retornar driver por id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Driver by Id", description = "Method to find a driver by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Driver found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))}),
            @ApiResponse(responseCode = "404", description = "Driver not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Driver> findDriverById(@PathVariable("id") Long id) {
        try {
            Optional<Driver> driver = driverService.getById(id);
            if (driver.isPresent())
                return new ResponseEntity<>(driver.get(), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Driver", description = "Method to create a driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Driver created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))}),
            @ApiResponse(responseCode = "404", description = "Driver not created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Driver> insertDriver(@Valid @RequestBody Driver driver) {
        try {
            Driver driverNew = driverService.save(driver);
            return ResponseEntity.status(HttpStatus.CREATED).body(driverNew);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Driver", description = "Method to update a driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Driver updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))}),
            @ApiResponse(responseCode = "404", description = "Driver not updated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Driver> updateDriver(@PathVariable("id") Long id,
                                               @Valid @RequestBody Driver driver) {
        try {
            Optional<Driver> driverUpdate = driverService.getById(id);
            if (!driverUpdate.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            driver.setId(id);
            driverService.save(driver);
            return new ResponseEntity<>(driver, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete Driver", description = "Method to delete a driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Driver deleted"),
            @ApiResponse(responseCode = "404", description = "Driver not deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Driver> deleteDriver(@PathVariable("id") Long id) {
        try {
            Optional<Driver> driverDelete = driverService.getById(id);
            if (!driverDelete.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            driverService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retornar driver por email y password [ POR VER ]
    @GetMapping(value = "/searchEmailPassword/{email}/{password}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Driver> findDriverByEmailAndPassword(
            @PathVariable("email") String email,
            @PathVariable("password") String password) {
        try {
            Driver driver = driverService.findByEmailAndPassword(email, password);
            if (driver == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(driver, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Save Driver Comment", description = "Method to save a comment for a driver")
    @ApiResponse(responseCode = "201", description = "Comment saved",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(value = "/{driverId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> saveDriverComment(@PathVariable("driverId") Long driverId, @Valid @RequestBody Comment comment) {
        try {
            Comment savedComment = driverService.saveComment(driverId, comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Driver and Comments", description = "Method to get a driver and their comments")
    @ApiResponse(responseCode = "200", description = "Driver and comments found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Driver.class))})
    @ApiResponse(responseCode = "404", description = "Driver not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/{driverId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getDriverAndComments(@PathVariable("driverId") Long driverId) {
        try {
            Map<String, Object> driverAndComments = driverService.getDriverAndComment(driverId);
            if (driverAndComments.containsKey("Mensaje")) {
                return new ResponseEntity<>(driverAndComments, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(driverAndComments, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Save Driver Experience", description = "Method to save an experience for a driver")
    @ApiResponse(responseCode = "201", description = "Experience saved",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Experience.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(value = "/{driverId}/experiences", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Experience> saveDriverExperience(@PathVariable("driverId") Long driverId, @Valid @RequestBody Experience experience) {
        try {
            Experience savedExperience = driverService.saveExperience(driverId, experience);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedExperience);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Driver and Experiences", description = "Method to get a driver and their experiences")
    @ApiResponse(responseCode = "200", description = "Driver and experiences found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Driver.class))})
    @ApiResponse(responseCode = "404", description = "Driver not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/{driverId}/experiences", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getDriverAndExperiences(@PathVariable("driverId") Long driverId) {
        try {
            Map<String, Object> driverAndExperiences = driverService.getDriverAndExperience(driverId);
            if (driverAndExperiences.containsKey("Mensaje")) {
                return new ResponseEntity<>(driverAndExperiences, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(driverAndExperiences, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Save Driver Vehicle", description = "Method to save a vehicle for a driver")
    @ApiResponse(responseCode = "201", description = "Vehicle saved",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vehicle.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(value = "/{driverId}/vehicles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vehicle> saveDriverVehicle(@PathVariable("driverId") Long driverId, @Valid @RequestBody Vehicle vehicle) {
        try {
            Vehicle savedVehicle = driverService.saveVehicle(driverId, vehicle);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Driver and Vehicles", description = "Method to get a driver and their vehicles")
    @ApiResponse(responseCode = "200", description = "Driver and vehicles found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Driver.class))})
    @ApiResponse(responseCode = "404", description = "Driver not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/{driverId}/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getDriverAndVehicles(@PathVariable("driverId") Long driverId) {
        try {
            Map<String, Object> driverAndVehicles = driverService.getDriverAndVehicle(driverId);
            if (driverAndVehicles.containsKey("Mensaje")) {
                return new ResponseEntity<>(driverAndVehicles, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(driverAndVehicles, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Save Driver Card", description = "Method to save a card for a driver")
    @ApiResponse(responseCode = "201", description = "Card saved",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Driver.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(value = "/{driverId}/cards", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Card> saveDriverCard(@PathVariable("driverId") Long driverId, @Valid @RequestBody Card card) {
        try {
            Card savedCard = driverService.saveCard(driverId, card);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Driver and Cards", description = "Method to get a driver and their cards")
    @ApiResponse(responseCode = "200", description = "Driver and cards found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Driver.class))})
    @ApiResponse(responseCode = "404", description = "Driver not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/{driverId}/cards", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getDriverAndCards(@PathVariable("driverId") Long driverId) {
        try {
            Map<String, Object> driverAndCards = driverService.getDriverAndCard(driverId);
            if (driverAndCards.containsKey("Mensaje")) {
                return new ResponseEntity<>(driverAndCards, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(driverAndCards, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}

