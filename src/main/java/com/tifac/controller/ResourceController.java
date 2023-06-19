package com.tifac.controller;

import com.tifac.Payloads.Message;
import com.tifac.Payloads.PageResult.PageableDto;
import com.tifac.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.OK;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/youtube")
@CrossOrigin(origins = "https://tifac.pranavbisaria.tech")
public class ResourceController {
    private final VideoService videoService;

    @Value("${password}")
    private String password;

    private LocalDateTime lastCallTime;

    // Get the channel Info
    @GetMapping()
    public ResponseEntity<?> getChannelInformation() {
        return this.videoService.getChannelInfo();
    }

    // Get video by Id
    @GetMapping("/getVideoById/{videoId}")
    public ResponseEntity<?> getVideo(@PathVariable("videoId") String videoId) {
        return this.videoService.getVideoById(videoId);
    }

    // Get playlist by Id
    @GetMapping("/getPlaylistById/{playlistId}")
    public ResponseEntity<?> getPlaylist(@PathVariable("playlistId") String playlistId) {
        return this.videoService.getPlaylistById(playlistId);
    }

    // Get all videos
    @GetMapping("/getAllVideos")
    public ResponseEntity<?> getAllVideos(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                             @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                             @RequestParam(value = "sortBy", defaultValue = "publishedAt", required = false) String sortBy,
                                             @RequestParam(value = "sortDir", defaultValue = "des", required = false) String sortDir
    ) {
        return new ResponseEntity<>(this.videoService.getAllVideos(new PageableDto(pageNumber, pageSize, sortBy, sortDir)), OK);
    }

    // Get videos in a playlist
    @GetMapping("/getAllVideosOfAPlayList")
    public ResponseEntity<?> getAllVideos(@RequestParam("playlistId") String playlistId,
                                          @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                          @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                          @RequestParam(value = "sortBy", defaultValue = "publishedAt", required = false) String sortBy,
                                          @RequestParam(value = "sortDir", defaultValue = "des", required = false) String sortDir
    ) {
        return new ResponseEntity<>(this.videoService.getVideosInAPlayList(playlistId, new PageableDto(pageNumber, pageSize, sortBy, sortDir)), OK);
    }

    // Get All Playlists
    @GetMapping("/getAllPlayLists")
    public ResponseEntity<?> getAllPlatLists(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "des", required = false) String sortDir
    ) {
        return new ResponseEntity<>(this.videoService.getAllPlatList(new PageableDto(pageNumber, pageSize, sortBy, sortDir)), OK);
    }

    // Search for a video
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String value,
                                    @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                    @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = "des", required = false) String sortDir
    ) {
        return new ResponseEntity<>(this.videoService.searchVideos(value, new PageableDto(pageNumber, pageSize, sortBy, sortDir)), OK);
    }

    // Search for a playlist
    @GetMapping("/searchPlayList")
    public ResponseEntity<?> searchPlayList(@RequestParam String value,
                                            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                            @RequestParam(value = "sortDir", defaultValue = "des", required = false) String sortDir
    ) {
        return new ResponseEntity<>(this.videoService.searchPlaylist(value, new PageableDto(pageNumber, pageSize, sortBy, sortDir)), OK);
    }

    //--------------------------------------------------- WebScrap Videos -------------------------------------------------------------
    //WebScrap All the resources
    @GetMapping("/webScrapResources")
    public ResponseEntity<?> webScrapResources(@RequestParam("passscode") String passcode) {
        if(!passcode.equals(password))
            return new ResponseEntity<>(new Message("The Password is Incorrect"), HttpStatus.BAD_REQUEST);

        LocalDateTime currentTime = LocalDateTime.now();
        if (this.lastCallTime != null && lastCallTime.plusDays(1).isAfter(currentTime)) {
            return new ResponseEntity<>(new Message("API can only be called once a day, try after "+ (currentTime.getHour() - this.lastCallTime.getHour()) + " hours!!"), HttpStatus.TOO_MANY_REQUESTS);
        }
        this.lastCallTime = currentTime;

        this.videoService.webScrapResouresFromYoutube();
        return new ResponseEntity<>(new Message("The Youtube Response Has Been Fetched and Saved"), OK);
    }
}
