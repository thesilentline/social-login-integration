package com.oauth.oauth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class DeviceController {

    @GetMapping("/device")
    public String getDevice() {
        return "device";
    }

    @GetMapping("/api/user")
    public ResponseEntity<Map<String, Object>> getUserInfo(OAuth2AuthenticationToken auth) {

        if (auth == null) {
            return ResponseEntity.ok(null);
        }

        String email = auth.getPrincipal().getAttribute("email");
        String picture = auth.getPrincipal().getAttribute("picture");

        if (picture == null) {
            picture = "default.png";
        }

        Map<String, Object> map = Map.of(
                "email", email,
                "picture", picture
        );

        return ResponseEntity.ok(map);
    }

    @GetMapping("/api/info")
    public ResponseEntity<Map<String, String>> getInfo() {
        var map = Map.of("1", "ADMIN info");
        return ResponseEntity.ok(map);
    }
}
