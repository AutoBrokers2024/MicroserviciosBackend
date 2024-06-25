package com.autoBrokers.driver_service.feigndriver;

import com.autoBrokers.driver_service.model.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "comment-service",url = "http://localhost:8080")
@RequestMapping("/api/v1/comments")
public interface CommentFeignDriver {

    @PostMapping()
    public Comment save(@RequestBody Comment comment);

    @GetMapping("/driver/{id}")
    public List<Comment> getComments(@PathVariable("id") Long driverId);
}
