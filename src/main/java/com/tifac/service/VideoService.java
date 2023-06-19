package com.tifac.service;

import com.tifac.Payloads.PageResult.PageResponse;
import com.tifac.Payloads.PageResult.PageableDto;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;

public interface VideoService {
    void webScrapResouresFromYoutube();

    @Scheduled(cron = "0 0 2 * * ?", zone = "Asia/Kolkata")
    void webScrapVideoScheduler();

    void updateVideo(JSONObject jsonObject);

    PageResponse searchVideos(String value, PageableDto pageable);

    PageResponse searchPlaylist(String value, PageableDto pageable);

    PageResponse getAllVideos(PageableDto pageable);

    PageResponse getVideosInAPlayList(String playListId, PageableDto pageable);

    PageResponse getAllPlatList(PageableDto pageable);

    ResponseEntity<?> getPlaylistById(String playlistId);

    ResponseEntity<?> getVideoById(String videoId);

    ResponseEntity<?> getChannelInfo();
}
