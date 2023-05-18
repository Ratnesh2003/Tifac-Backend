package com.example.tifac_backend.controller;

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
//    private final

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
        JSONObject jsonObject = XML.toJSONObject(notification);
        String jsonString = jsonObject.toString();
        System.out.println(jsonString);
        return ResponseEntity.status(OK).body(notification);
    }
}
