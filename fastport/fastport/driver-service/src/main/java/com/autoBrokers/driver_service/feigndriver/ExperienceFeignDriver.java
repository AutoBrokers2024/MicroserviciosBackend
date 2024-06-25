package com.autoBrokers.driver_service.feigndriver;

import com.autoBrokers.driver_service.model.Comment;
import com.autoBrokers.driver_service.model.Experience;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(name = "experience-service",url = "http://localhost:8080")
@RequestMapping("/api/v1/experiences")
public interface ExperienceFeignDriver {

    @PostMapping()
    public Experience save(@RequestBody Experience experience);

    @GetMapping("/driver/{id}")
    public List<Experience> getExperiences(@PathVariable("id") Long driverId);

}
