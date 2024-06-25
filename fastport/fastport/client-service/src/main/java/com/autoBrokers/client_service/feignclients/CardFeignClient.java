package com.autoBrokers.client_service.feignclients;

import com.autoBrokers.client_service.model.Card;
import com.autoBrokers.client_service.model.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "card-service",url = "http://localhost:8080")
@RequestMapping("/api/v1/cards")
public interface CardFeignClient {

    @PostMapping()
    public Card save(@RequestBody Card card);

    @GetMapping("/client/{id}")
    public List<Card> getCards(@PathVariable("id") Long clientId);
}
