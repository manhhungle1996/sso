package vn.com.atomi.openbanking.authservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.atomi.openbanking.authen.KeycloakService;
import vn.com.atomi.openbanking.authservice.utils.Constants;

@RestController
@RequestMapping("/public")
public class TestController {
    @Autowired
    private KeycloakService keycloakService;
    @PostMapping("/v1/token")
    public ResponseEntity<?> extAuthConnected() {
        return ResponseEntity.ok(keycloakService.obtainAccessToken());
    }
}