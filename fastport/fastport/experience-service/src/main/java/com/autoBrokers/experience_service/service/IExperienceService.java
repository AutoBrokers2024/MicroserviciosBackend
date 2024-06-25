package com.autoBrokers.experience_service.service;

import com.autoBrokers.experience_service.entities.Experience;

import java.util.List;

public interface IExperienceService extends CrudService<Experience>{
    List<Experience> findByDriverId(Long driverId) throws Exception;
}
