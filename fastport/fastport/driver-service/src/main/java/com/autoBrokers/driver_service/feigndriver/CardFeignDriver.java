package com.autoBrokers.driver_service.feigndriver;

import com.autoBrokers.driver_service.model.Card;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "card-service",url = "http://localhost:8080")
@RequestMapping("/api/v1/cards")
public interface CardFeignDriver {

    @PostMapping()
    public Card save(@RequestBody Card card);

    @GetMapping("/driver/{id}")
    public List<Card> getCards(@PathVariable("id") Long driverId);
}
