package com.example.tifac_backend.controller;

import com.example.tifac_backend.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class UpdateController {
    private final VideoService service;

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
    public ResponseEntity<String> notification(@RequestBody String notification) {
//        System.out.println(notification + " zero print");
        JSONObject jsonObject = XML.toJSONObject(notification);
//        System.out.println(jsonObject + "zero.1 print");
        String jsonString = jsonObject.toString();
        System.out.println(jsonString + "  first print");
        service.updateVideo(jsonString);
        return ResponseEntity.status(OK).body(jsonString);
    }
}
