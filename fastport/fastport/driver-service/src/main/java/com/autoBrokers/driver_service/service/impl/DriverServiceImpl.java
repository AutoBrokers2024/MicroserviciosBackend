package com.autoBrokers.driver_service.service.impl;


import com.autoBrokers.driver_service.entities.Driver;
import com.autoBrokers.driver_service.feigndriver.CardFeignDriver;
import com.autoBrokers.driver_service.feigndriver.CommentFeignDriver;
import com.autoBrokers.driver_service.feigndriver.ExperienceFeignDriver;
import com.autoBrokers.driver_service.feigndriver.VehicleFeignDriver;
import com.autoBrokers.driver_service.model.Card;
import com.autoBrokers.driver_service.model.Comment;
import com.autoBrokers.driver_service.model.Experience;
import com.autoBrokers.driver_service.model.Vehicle;
import com.autoBrokers.driver_service.repository.IDriverRepository;
import com.autoBrokers.driver_service.service.IDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true) // Solo servicios de lectura
public class DriverServiceImpl implements IDriverService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CommentFeignDriver commentFeignDriver;

    @Autowired
    private CardFeignDriver cardFeignDriver;

    @Autowired
    private VehicleFeignDriver vehicleFeignDriver;
    @Autowired
    private ExperienceFeignDriver experienceFeignDriver;
    private final IDriverRepository driverRepository;

    public DriverServiceImpl(IDriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    @Transactional
    public Driver save(Driver driver) throws Exception {
        return driverRepository.save(driver);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        driverRepository.deleteById(id);
    }

    @Override
    public List<Driver> getAll() throws Exception {
        return driverRepository.findAll();
    }

    @Override
    public Optional<Driver> getById(Long id) throws Exception {
        return driverRepository.findById(id);
    }

    @Override
    public Driver findByEmailAndPassword(String email, String password) {
        return driverRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public List<Comment> getComment(int commentId) {
        List<Comment> comment = restTemplate.getForObject("http://localhost:8080/api/v1/comments/" + commentId, List.class);
        return comment;
    }



    @Override
    public Comment saveComment(Long driverId, Comment comment) {
        comment.setIdDriver(driverId);
        Comment nuevoComment = commentFeignDriver.save(comment);
        return nuevoComment;
    }

    @Override
    public Map<String, Object> getDriverAndComment(Long driverId){
        Map<String,Object> resultado = new HashMap<>();
        Driver driver = driverRepository.findById(driverId).orElse(null);

        if(driver == null) {
            resultado.put("Mensaje", "El driver no existe");
            return resultado;
        }

        resultado.put("Driver",driver);
        List<Comment> comments = commentFeignDriver.getComments(driverId);
        if(comments.isEmpty()) {
            resultado.put("Comentarios", "El driver no tiene comentarios");
        }
        else {
            resultado.put("Comentarios", comments);
        }

        return resultado;
    }


    @Override
    public List<Experience> getExperience(int experienceId) {
        List<Experience> experience = restTemplate.getForObject("http://localhost:8080/api/v1/experiences/" + experienceId, List.class);
        return experience;
    }

    @Override
    public Experience saveExperience(Long driverId, Experience experience) {
        experience.setIdDriver(driverId);
        Experience nuevoExperience = experienceFeignDriver.save(experience);
        return nuevoExperience;
    }

    @Override
    public Map<String, Object> getDriverAndExperience(Long driverId) {
        Map<String, Object> resultado = new HashMap<>();
        Driver driver = driverRepository.findById(driverId).orElse(null);

        if (driver == null) {
            resultado.put("Mensaje", "El driver no existe");
            return resultado;
        }

        resultado.put("Driver", driver);
        List<Experience> experiences = experienceFeignDriver.getExperiences(driverId);
        if (experiences.isEmpty()) {
            resultado.put("Experiencias", "El driver no tiene experiencias");
        } else {
            resultado.put("Experiencias", experiences);
        }

        return resultado;
    }

    @Override
    public List<Vehicle> getVehicle(int vehicleId) {
        List<Vehicle> vehicle = restTemplate.getForObject("http://localhost:8080/api/v1/vehicles/" + vehicleId, List.class);
        return vehicle;
    }

    @Override
    public Vehicle saveVehicle(Long driverId, Vehicle vehicle) {
        vehicle.setIdDriver(driverId);
        Vehicle nuevoVehicle = vehicleFeignDriver.save(vehicle);
        return nuevoVehicle;
    }

    @Override
    public Map<String, Object> getDriverAndVehicle(Long driverId) {
        Map<String, Object> resultado = new HashMap<>();
        Driver driver = driverRepository.findById(driverId).orElse(null);

        if (driver == null) {
            resultado.put("Mensaje", "El driver no existe");
            return resultado;
        }

        resultado.put("Driver", driver);
        List<Vehicle> vehicles = vehicleFeignDriver.getVehicles(driverId);
        if (vehicles.isEmpty()) {
            resultado.put("Vehículos", "El driver no tiene vehículos");
        } else {
            resultado.put("Vehículos", vehicles);
        }

        return resultado;
    }


    @Override
    public List<Card> getCard(int cardId) {
        return restTemplate.getForObject("http://localhost:8080/api/v1/cards/" + cardId, List.class);
    }

    @Override
    @Transactional
    public Card saveCard(Long driverId, Card card) {
        card.setIdDriver(driverId);
        return cardFeignDriver.save(card);
    }

    @Override
    public Map<String, Object> getDriverAndCard(Long driverId) {
        Map<String, Object> resultado = new HashMap<>();
        Driver driver = driverRepository.findById(driverId).orElse(null);

        if (driver == null) {
            resultado.put("Mensaje", "El driver no existe");
            return resultado;
        }

        resultado.put("Driver", driver);
        List<Card> cards = cardFeignDriver.getCards(driverId);
        if (cards.isEmpty()) {
            resultado.put("Comentarios", "El driver no tiene comentarios");
        } else {
            resultado.put("Comentarios", cards);
        }

        return resultado;
    }



}

