package com.autoBrokers.notification_service.service.impl;

import com.autoBrokers.notification_service.entities.Notification;
import com.autoBrokers.notification_service.feignotification.ContractFeignClient;
import com.autoBrokers.notification_service.model.Contract;
import com.autoBrokers.notification_service.repository.INotificationRepository;
import com.autoBrokers.notification_service.service.INotificationService;
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
public class NotificationServiceImpl implements INotificationService {


    @Autowired
    private RestTemplate restTemplate;

    private final  INotificationRepository notificationRepository;

    @Autowired
    private ContractFeignClient contractFeignClient;

    public NotificationServiceImpl(INotificationRepository notificationRepository) {
        this.notificationRepository= notificationRepository;
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void delete(Long id) throws Exception {
        notificationRepository.deleteById(id);
    }

    @Override
    public Optional<Notification> getById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }


    @Override
    public List<Contract> getContract(int contractId) {
        return restTemplate.getForObject("http://localhost:8080/api/v1/contracts/" + contractId, List.class);
    }

    @Override
    @Transactional
    public Contract saveContract(Long notificationId, Contract contract) {
        contract.setNotificationId(notificationId);
        return contractFeignClient.save(contract);
    }

    @Override
    public Map<String, Object> getNotificationtAndContract(Long notificationId) {
        Map<String, Object> resultado = new HashMap<>();
        Notification notification = notificationRepository.findById(notificationId).orElse(null);

        if (notification == null) {
            resultado.put("Mensaje", "El cliente no existe");
            return resultado;
        }

        resultado.put("Notification", notification);
        List<Contract> contracts = contractFeignClient.getContracts(notificationId);
        if (contracts.isEmpty()) {
            resultado.put("Notificaciones", "El cliente no tiene contrato");
        } else {
            resultado.put("Contratos", contracts);
        }

        return resultado;
    }


}
