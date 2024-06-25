package com.autoBrokers.notification_service.repository;

import com.autoBrokers.notification_service.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {


}