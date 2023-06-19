package com.tifac.controller;

import com.tifac.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/youtube")
@CrossOrigin(origins = "https://tifac.pranavbisaria.tech")
public class UpdateController {
    private final VideoService videoService;

    @Value("${token}")
    private String token;

    // Webhooks subscription
    @GetMapping("/pubsubhubbub")
    public ResponseEntity<String> verify(@RequestParam("hub.mode") String mode,
                                         @RequestParam("hub.verify_token") String verifyToken,
                                         @RequestParam("hub.challenge") String challenge) {
        if (mode.equals("subscribe") && verifyToken.equals(token)) {
            System.out.println("\n\nThe webhook has been subscribed!!!\n");
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // Pubsubhubbub request point
    @PostMapping("/pubsubhubbub")
    public ResponseEntity<String> notification(@RequestBody String notification) {
        System.out.println("\n\nGot a notification!!!\n");
        JSONObject jsonObject = XML.toJSONObject(notification);
        this.videoService.updateVideo(jsonObject);
        return ResponseEntity.status(OK).body(notification);
    }
}
