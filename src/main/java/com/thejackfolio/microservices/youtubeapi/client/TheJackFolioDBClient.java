package com.thejackfolio.microservices.youtubeapi.client;

import com.thejackfolio.microservices.youtubeapi.models.InterestedGame;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "DATABASE-SERVICE")
public interface TheJackFolioDBClient {

    @GetMapping("/games/get-interested-games/{email}")
    public ResponseEntity<List<InterestedGame>> findAllInterestedGamesForUser(@PathVariable String email);
}
