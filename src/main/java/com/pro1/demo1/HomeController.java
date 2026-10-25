//
//package com.pro1.demo1;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class HomeController {
//
//    @GetMapping("/a")
//    public String home() {
//        return """
//                <html>
//                    <head>
//                        <title>My Spring Boot App</title>
//                    </head>
//                    <body style="font-family: Arial; text-align: center; margin-top: 100px;">
//                        <h1>Welcome!</h1>
//                        <p>Your Spring Boot application is running successfully.</p>
//                    </body>
//                </html>
//                """;
//    }
//}

package com.pro1.demo1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/a")
    @ResponseBody
    public String home(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String department) {

        if (name == null || email == null ||
                phone == null || department == null) {
            return """
                <h2>Student Registration Form</h2>
                <p>Open the registration form from the home page.</p>
                <a href="/">Go Back</a>
                """;
        }

        return """
            <html>
            <body style="font-family:Arial;text-align:center;margin-top:80px">
                <h1>Registration Successful!</h1>
                <h3>Student Details</h3>
                <p>Name: %s</p>
                <p>Email: %s</p>
                <p>Phone: %s</p>
                <p>Department: %s</p>
                <a href="/">Register Another Student</a>
            </body>
            </html>
            """.formatted(
                escapeHtml(name),
                escapeHtml(email),
                escapeHtml(phone),
                escapeHtml(department));
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
