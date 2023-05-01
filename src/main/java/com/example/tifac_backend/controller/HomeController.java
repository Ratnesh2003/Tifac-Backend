package com.example.tifac_backend.controller;

import com.example.tifac_backend.service.FeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final FeignClient feignClient;

    @Value("${key}")
    private String key;
    @Value("${channelId}")
    private String channelId;

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String type, @RequestParam String part, @RequestParam int maxResults, @RequestParam(defaultValue = "relevance", required = false) String order, @RequestParam String q, @RequestParam(required = false) String pageToken) {
        return feignClient.getSearchResults(key, type, channelId, part, maxResults, order, q, pageToken);
    }

    @GetMapping("/playlist")
    public ResponseEntity<?> playlist(@RequestParam String playlistId, @RequestParam String part, @RequestParam int maxResults, @RequestParam(required = false) String pageToken) {
        return feignClient.getPlaylistItems(key, part, playlistId, maxResults, pageToken);
    }

    @GetMapping("/")
    public ResponseEntity<?> home() {
        return ResponseEntity.status(HttpStatus.OK).body("This is home route");
    }
}
