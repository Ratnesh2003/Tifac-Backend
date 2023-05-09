package com.example.tifac_backend.controller;

import com.example.tifac_backend.Models.PlayList;
import com.example.tifac_backend.Payloads.PageResult.PageableDto;
import com.example.tifac_backend.Repository.PLayListRepo;
import com.example.tifac_backend.service.Impl.VideoServiceImpl;
import com.example.tifac_backend.service.VideoService;
import com.example.tifac_backend.service.YoutubeApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/youtube")
public class HomeController {
    private final VideoService videoService;
    private final VideoServiceImpl videoServiceI;
    @GetMapping("/")
    public ResponseEntity<?> home() {
        this.videoServiceI.getTheVideo();
        return ResponseEntity.status(HttpStatus.OK).body("This is home route");
    }
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String value,
                                    @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                    @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = "des", required = false) String sortDir
    ){
        return new ResponseEntity<>(this.videoService.searchVideos(value, new PageableDto(pageNumber, pageSize, sortBy, sortDir)), OK);
    }

    @GetMapping("/getAllVideos")
    public ResponseEntity<?> getAllPlayLists(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                             @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                             @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                             @RequestParam(value = "sortDir", defaultValue = "des", required = false) String sortDir
    ){
        return new ResponseEntity<>(this.videoService.getAllVideos(new PageableDto(pageNumber, pageSize, sortBy, sortDir)), OK);
    }

    @GetMapping("/getAllVideosOfAPlayList")
    public ResponseEntity<?> getAllVideos(@RequestParam("playlistId") String playlistId,
                                          @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                          @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                          @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                          @RequestParam(value = "sortDir", defaultValue = "des", required = false) String sortDir
    ){
        return new ResponseEntity<>(this.videoService.getVideosInAPlayList(playlistId, new PageableDto(pageNumber, pageSize, sortBy, sortDir)), OK);
    }

    @GetMapping("/webScrap")
    public ResponseEntity<?> webScrap() {
        return this.videoService.webScrapVideos();
    }
    @GetMapping("/webScrapPlayList")
    public ResponseEntity<?> webscrapPlayList() {
        return this.videoService.webScrapPlayList();
    }
}
