package com.example.tifac_backend.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class YoutubeApiService {
    private final FeignClient feignClient;

    private final Cache<String, Object> cache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(2, TimeUnit.HOURS)
            .build();

    public YoutubeApiService(FeignClient feignClient) {
        this.feignClient = feignClient;
    }

    public ResponseEntity<?> getSearchResults(String key, String channelId, String type, String part, int maxResults, String order, String q, String pageToken) {
        String cacheKey = String.format("%s:%s:%s:%s:%d:%s:%s:%s", key, channelId, type, part, maxResults, order, q, pageToken);
        Object cachedResponse = cache.getIfPresent(cacheKey);
        if (cachedResponse != null) {
            return (ResponseEntity<?>) cachedResponse;
        } else {
            ResponseEntity<?> response = feignClient.getSearchResults(key, type, channelId, part, maxResults, order, q, pageToken);
            cache.put(cacheKey, response);
            return response;
        }
    }

    public ResponseEntity<?> getPlaylistItems(String key, String part, String playlistId, int maxResults, String pageToken) {
        String cacheKey = String.format("%s:%s:%s:%d:%s", key, part, playlistId, maxResults, pageToken);
        Object cachedResponse = cache.getIfPresent(cacheKey);
        if (cachedResponse != null) {
            return (ResponseEntity<?>) cachedResponse;
        } else {
            ResponseEntity<?> response = feignClient.getPlaylistItems(key, part, playlistId, maxResults, pageToken);
            cache.put(cacheKey, response);
            return response;
        }
    }
}
