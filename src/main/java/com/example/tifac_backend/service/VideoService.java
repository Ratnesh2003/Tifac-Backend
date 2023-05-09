package com.example.tifac_backend.service;

import com.example.tifac_backend.Payloads.PageResult.PageResponse;
import com.example.tifac_backend.Payloads.PageResult.PageableDto;
import org.springframework.http.ResponseEntity;

public interface VideoService {
    ResponseEntity<?> webScrapVideos();

    ResponseEntity<?> webScrapPlayList();

    PageResponse searchVideos(String value, PageableDto pageable);

    PageResponse getAllVideos(PageableDto pageable);
}
