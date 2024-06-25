package com.autoBrokers.comment_service.service;

import com.autoBrokers.comment_service.entities.Comment;

import java.util.List;

public interface ICommentService extends CrudService<Comment> {

    List<Comment> findByDriverId(Long driverId) throws Exception;
    List<Comment> findByClientId(Long clientId) throws Exception;

}
