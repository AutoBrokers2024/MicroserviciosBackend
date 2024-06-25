package com.autoBrokers.client_service.feignclients;

import com.autoBrokers.client_service.model.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;



import java.util.List;



    @FeignClient(name = "comment-service",url = "http://localhost:8080")
    @RequestMapping("/api/v1/comments")
    public interface CommentFeignClient {

        @PostMapping()
        public Comment save(@RequestBody Comment comment);

        @GetMapping("/client/{id}")
        public List<Comment> getComments(@PathVariable("id") Long clientId);
    }

