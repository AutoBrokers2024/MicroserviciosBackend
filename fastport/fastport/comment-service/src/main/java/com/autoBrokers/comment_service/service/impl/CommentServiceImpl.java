package com.autoBrokers.comment_service.service.impl;

import com.autoBrokers.comment_service.entities.Comment;
import com.autoBrokers.comment_service.repository.ICommentRepository;
import com.autoBrokers.comment_service.service.ICommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true) // Solo servicios de lectura
public class CommentServiceImpl implements ICommentService {

    private final ICommentRepository commentRepository;

    public CommentServiceImpl(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public Comment save(Comment comment) throws Exception {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getAll() throws Exception {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> getById(Long id) throws Exception {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findByDriverId(Long driverId) throws Exception {
        return commentRepository.findByIdDriver(driverId);
    }

    @Override
    public List<Comment> findByClientId(Long clientId) throws Exception {
        return commentRepository.findByIdClient(clientId);
    }

}

