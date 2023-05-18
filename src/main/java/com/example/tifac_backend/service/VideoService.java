package com.example.tifac_backend.service;

import com.example.tifac_backend.Payloads.PageResult.PageResponse;
import com.example.tifac_backend.Payloads.PageResult.PageableDto;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public interface VideoService {
    void webScrapPlayListContent();

    void updateVideo(String jsonString);

    PageResponse searchVideos(String value, PageableDto pageable);

    PageResponse searchPlaylist(String value, PageableDto pageable);

    PageResponse getAllVideos(PageableDto pageable);

    PageResponse getVideosInAPlayList(String playListId, PageableDto pageable);

    PageResponse getAllPlatList(PageableDto pageable);

    ResponseEntity<?> getPlaylistById(String playlistId);

    ResponseEntity<?> getVideoById(String videoId);

    ResponseEntity<?> getChannelInfo();
}
