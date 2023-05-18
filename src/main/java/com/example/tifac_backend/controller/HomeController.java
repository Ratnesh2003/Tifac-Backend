package com.example.tifac_backend.controller;

import com.example.tifac_backend.Payloads.PageResult.PageableDto;
import com.example.tifac_backend.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;
import org.json.XML;


import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/youtube")
public class HomeController {
    private final VideoService videoService;
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
                                             @RequestParam(value = "sortBy", defaultValue = "publishedAt", required = false) String sortBy,
                                             @RequestParam(value = "sortDir", defaultValue = "des", required = false) String sortDir
    ){
        return new ResponseEntity<>(this.videoService.getAllVideos(new PageableDto(pageNumber, pageSize, sortBy, sortDir)), OK);
    }

    @GetMapping("/getAllVideosOfAPlayList")
    public ResponseEntity<?> getAllVideos(@RequestParam("playlistId") String playlistId,
                                          @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                          @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                          @RequestParam(value = "sortBy", defaultValue = "publishedAt", required = false) String sortBy,
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
        return this.videoService.webScrapPlayListContent();
    }
    @GetMapping("/getAllPlayLists")
            public ResponseEntity<?> getAllPlatLists(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "des", required = false) String sortDir
    ){
        return new ResponseEntity<>(this.videoService.getAllPlatList(new PageableDto(pageNumber, pageSize, sortBy, sortDir)), OK);
    }

    @GetMapping()
    public ResponseEntity<?> getChannelInformation(){
        return this.videoService.getChannelInfo();
    }
    @GetMapping("/getPlaylistById/{playlistId}")
    public ResponseEntity<?> getPlaylist(@PathVariable("playlistId") String playlistId){
        return this.videoService.getPlaylistById(playlistId);
    }
    @GetMapping("/getVideoById/{videoId}")
    public ResponseEntity<?> getVideo(@PathVariable("videoId") String videoId){
        return this.videoService.getVideoById(videoId);
    }
    @GetMapping("/searchPlayList")
    public ResponseEntity<?> searchPlayList(@RequestParam String value,
                                    @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                    @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = "des", required = false) String sortDir
    ){
        return new ResponseEntity<>(this.videoService.searchPlaylist(value, new PageableDto(pageNumber, pageSize, sortBy, sortDir)), OK);
    }

    @GetMapping("/pubsubhubbub")
    public ResponseEntity<String> verify(@RequestParam("hub.mode") String mode,
                                         @RequestParam("hub.verify_token") String verifyToken,
                                         @RequestParam("hub.challenge") String challenge) {
        if (mode.equals("subscribe") && verifyToken.equals("VERIFY_TOKEN")) {
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/pubsubhubbub")
    public ResponseEntity<?> notification(@RequestBody String notification) {
        JSONObject jsonObject = XML.toJSONObject(notification);
        return videoService.addNewVideo(jsonObject);
    }
}
