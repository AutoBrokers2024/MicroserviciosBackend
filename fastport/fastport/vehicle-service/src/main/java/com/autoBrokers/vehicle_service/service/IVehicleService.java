package com.autoBrokers.vehicle_service.service;

import com.autoBrokers.vehicle_service.entities.Vehicle;

import java.util.List;

public interface IVehicleService extends CrudService<Vehicle>{
    List<Vehicle> findByIdDriver(Long driverId) throws Exception;
    //List<Vehicle> findByType_cardQuantityCategory(String type_card, Long quantity, String category) throws Exception;
}
