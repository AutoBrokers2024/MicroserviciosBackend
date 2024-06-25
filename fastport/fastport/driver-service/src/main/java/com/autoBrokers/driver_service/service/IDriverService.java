package com.autoBrokers.driver_service.service;

import com.autoBrokers.driver_service.entities.Driver;
import com.autoBrokers.driver_service.model.Card;
import com.autoBrokers.driver_service.model.Comment;
import com.autoBrokers.driver_service.model.Experience;
import com.autoBrokers.driver_service.model.Vehicle;

import java.util.List;
import java.util.Map;

public interface IDriverService extends CrudService<Driver> {

    Driver findByEmailAndPassword(String email, String password);

    Comment saveComment(Long clientId, Comment comment);
    Map<String, Object> getDriverAndComment(Long clientId);

    List<Comment> getComment(int commentId);
    List<Experience> getExperience(int experienceId);
    Experience saveExperience(Long driverId, Experience experience);
    Map<String, Object> getDriverAndExperience(Long driverId);

    Vehicle saveVehicle(Long driverId, Vehicle vehicle);
    Map<String, Object> getDriverAndVehicle(Long driverId);
    List<Vehicle> getVehicle(int vehicleId);

    Card saveCard(Long driverId, Card card);
    Map<String, Object> getDriverAndCard(Long driverId);

    List<Card> getCard(int cardId);
}
