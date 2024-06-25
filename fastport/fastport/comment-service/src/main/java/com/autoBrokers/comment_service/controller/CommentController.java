package com.autoBrokers.comment_service.controller;


import com.autoBrokers.comment_service.entities.Comment;
import com.autoBrokers.comment_service.service.ICommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin
@RestController
@RequestMapping("/api/v1/comments")
@Tag(name="Comments", description="Web Service RESTful of Comments")
public class CommentController {

    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;

    }

    @Operation(summary = "List of Comments", description = "Method to list all comments")
    @ApiResponse(responseCode = "200", description = "Comments found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))})
    @ApiResponse(responseCode = "204", description = "Comments not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> findAllComments() {
        try {
            List<Comment> comments = commentService.getAll();

            if (!comments.isEmpty())
                return new ResponseEntity<>(comments, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Comment by Id", description = "Method to find a comment by id")
    @ApiResponse(responseCode = "200", description = "Comment found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))})
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> findCommentById(@PathVariable("id") Long id) {
        try {
            Optional<Comment> comment = commentService.getById(id);

            if (comment.isPresent())
                return new ResponseEntity<>(comment.get(), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/client/{id}")
    public ResponseEntity<List<Comment>> listarComentariosPorClienteId(@PathVariable("id") Long id) throws Exception {
        List<Comment> comments = commentService.findByClientId(id);
        if(comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comments);
    }



    @GetMapping("/driver/{id}")
    public ResponseEntity<List<Comment>> listarComentariosPorDriverId(@PathVariable("id") Long id) throws Exception {
        List<Comment> comments = commentService.findByClientId(id);
        if(comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comments);
    }


    @PostMapping()
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment) throws Exception {
        Comment nuevoComment = commentService.save(comment);
        return ResponseEntity.ok(nuevoComment);
    }





}

