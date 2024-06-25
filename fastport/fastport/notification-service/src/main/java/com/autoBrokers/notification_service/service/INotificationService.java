package com.autoBrokers.notification_service.service;

import com.autoBrokers.notification_service.entities.Notification;
import com.autoBrokers.notification_service.model.Contract;

import java.util.List;
import java.util.Map;

public interface INotificationService extends CrudService<Notification> {
    //Notification findByClient(Long id) throws Exception;

    Contract saveContract(Long clientId, Contract contract);
    Map<String, Object> getNotificationtAndContract(Long notificationId);
    List<Contract> getContract(int contractId);
}