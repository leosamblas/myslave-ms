package com.example.call.myslave;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "vthreads-service", url = "http://vthreads-service.development:8080")
public interface VthreadsClient {

    @GetMapping("/hello")
    public ResponseEntity<String> hello();

    @GetMapping("/teste")
    public ResponseEntity<String> teste(@RequestParam("param") String name);
}
