package com.autoBrokers.vehicle_service.service.impl;


import com.autoBrokers.vehicle_service.entities.Vehicle;
import com.autoBrokers.vehicle_service.repository.IVehicleRepository;
import com.autoBrokers.vehicle_service.service.IVehicleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class VehicleServiceImpl implements IVehicleService {

    private final IVehicleRepository vehicleRepository;
    public VehicleServiceImpl(IVehicleRepository vehicleRepository){this.vehicleRepository = vehicleRepository;}

    @Override
    @Transactional
    public Vehicle save(Vehicle vehicle) throws Exception {
        return vehicleRepository.save(vehicle);
    }
    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        vehicleRepository.deleteById(id);
    }
    @Override
    public List<Vehicle> findByIdDriver(Long driverId) throws Exception {
        return vehicleRepository.findByIdDriver(driverId);
    }
    @Override
    public List<Vehicle> getAll() throws Exception {
        return vehicleRepository.findAll();
    }
    @Override
    public Optional<Vehicle> getById(Long id) throws Exception {
        return vehicleRepository.findById(id);
    }
    //@Override
    //public List<Vehicle> finByType_cardQuantityCategory(String type_card, Long quantity, String category) {
    //    return vehicleRepository.finByType_cardQuantityCategory(type_card,quantity,category);
    //}



}