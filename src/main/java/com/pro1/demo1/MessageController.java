
package com.pro1.demo1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @GetMapping("/api/message")
    public String getMessage() {
        return "Hello bro! This message comes from the Java backend.";
    }
}
