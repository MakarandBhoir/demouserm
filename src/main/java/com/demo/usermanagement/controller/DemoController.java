package com.demo.usermanagement.controller;

import com.demo.usermanagement.model.User;
import com.demo.usermanagement.service.UserService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/slow")
    public ResponseEntity<Map<String, Object>> slowResponse() throws InterruptedException {
        logger.info("GET /demo/slow called");
        return ResponseEntity.ok(userService.simulateSlowResponse());
    }

    @GetMapping("/sensitive-data")
    public ResponseEntity<Map<String, Object>> sensitiveData() {
        logger.warn("GET /demo/sensitive-data called");
        return ResponseEntity.ok(userService.getSensitiveData());
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersUnsafe(@RequestParam String email) {
        logger.warn("GET /demo/search called with email={}", email);
        return ResponseEntity.ok(userService.searchUsersByEmailUnsafe(email));
    }

    @GetMapping(value = "/xss", produces = MediaType.TEXT_HTML_VALUE)
    public String xssDemo(@RequestParam(defaultValue = "<h1>Hello Demo</h1>") String input) {
        logger.warn("GET /demo/xss called with input={}", input);
        String safeInput = HtmlUtils.htmlEscape(input);
        return "<html><body><h2>XSS Demo</h2><div>" + safeInput + "</div></body></html>";
    }
}