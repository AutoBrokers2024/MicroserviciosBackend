package com.autoBrokers.driver_service.feigndriver;

import com.autoBrokers.driver_service.model.Experience;
import com.autoBrokers.driver_service.model.Vehicle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "vehicle-service",url = "http://localhost:8080")
@RequestMapping("/api/v1/vehicles")
public interface VehicleFeignDriver {

    @PostMapping()
    public Vehicle save(@RequestBody Vehicle vehicle);

    @GetMapping("/driver/{id}")
    public List<Vehicle> getVehicles(@PathVariable("id") Long driverId);

}
