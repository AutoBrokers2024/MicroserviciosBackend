package com.autoBrokers.experience_service.repository;

import com.autoBrokers.experience_service.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByIdDriver(Long idDriver);
}
