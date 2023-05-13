package com.example.tifac_backend.service;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@org.springframework.cloud.openfeign.FeignClient(name = "youtube-api", url = "https://youtube.googleapis.com/youtube/v3")
public interface FeignClient {
    @GetMapping("/search")
    ResponseEntity<?> getSearchResults(@RequestParam String key, @RequestParam String type, @RequestParam String channelId, @RequestParam String part, @RequestParam int maxResults, @RequestParam(defaultValue = "relevance", required = false) String order, @RequestParam String q, @RequestParam(required = false) String pageToken);

    @GetMapping("/playlistItems")
    ResponseEntity<?> getPlaylistItems(@RequestParam String key, @RequestParam String part, @RequestParam String playlistId, @RequestParam int maxResults, @RequestParam(required = false) String pageToken);

    @GetMapping("/playlists")
    ResponseEntity<?> getAllPlaylist(@RequestParam String key, @RequestParam String part, @RequestParam String channelId, @RequestParam int maxResults, @RequestParam(required = false) String pageToken);

    @GetMapping("/channels/")
    ResponseEntity<?> getChannelDetails(@RequestParam String key, @RequestParam String part, @RequestParam String id);
}
