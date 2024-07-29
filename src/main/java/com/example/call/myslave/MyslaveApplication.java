package com.example.call.myslave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@RestController
@EnableFeignClients
@SpringBootApplication
public class MyslaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyslaveApplication.class, args);
	}

	private VthreadsClient vthreadsClient;

	public MyslaveApplication(VthreadsClient vthreadsClient) {
		this.vthreadsClient = vthreadsClient;
	}

	@GetMapping("/newEndpoint")
	public ResponseEntity<String> newEndpoint() {
		log.info("Calling vthreads outside");
		return ResponseEntity.ok("Hello message from myslave");
	}

	@GetMapping("/teste")
	public ResponseEntity<String> teste() {
		log.info("Calling vthreads outside");
		return ResponseEntity.ok("Hello message from myslave");
	}

	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		log.info("Calling vthreads outside");
		String message = vthreadsClient.hello().getBody();
		return ResponseEntity.ok(message.concat(" message from myslave"));
	}

	@GetMapping("/message")
	public ResponseEntity<String> helloMyMessage(@RequestParam("param") String name) {
		log.info("Calling vthreads outside: " + name);
		String message = vthreadsClient.teste(name).getBody();
		return ResponseEntity.ok("Hello ".concat(message).concat(" message from myslave"));
	}
}
