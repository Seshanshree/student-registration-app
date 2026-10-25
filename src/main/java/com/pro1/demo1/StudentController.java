
package com.pro1.demo1;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

@Controller
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/students")
    @ResponseBody
    public String registerStudent(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String department) {

        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setPhone(phone);
        student.setDepartment(department);

        repository.save(student);

        return """
            <!DOCTYPE html>
            <html>
            <head><title>Registration Success</title></head>
            <body style="font-family:Arial;text-align:center;margin-top:80px">
                <h1>Registration Successful!</h1>
                <p>Student saved successfully in MySQL.</p>
                <p>Name: %s</p>
                <p>Email: %s</p>
                <p><a href="/">Register Another Student</a></p>
                <p><a href="/students">View All Students</a></p>
            </body>
            </html>
            """.formatted(
                escape(name),
                escape(email));
    }

    @GetMapping("/students")
    @ResponseBody
    public String viewStudents() {

        List<Student> students = repository.findAll();
        StringBuilder html = new StringBuilder();

        html.append("""
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <title>Student Records</title>
                <style>
                    * { box-sizing: border-box; }
                    body {
                        font-family: Arial, sans-serif;
                        background: #f1f5f9;
                        margin: 0;
                        padding: 35px 20px;
                        color: #1e293b;
                    }
                    .container {
                        max-width: 1100px;
                        margin: auto;
                        background: white;
                        padding: 28px;
                        border-radius: 14px;
                        box-shadow: 0 5px 20px #0000000d;
                        overflow-x: auto;
                    }
                    h1 { color: #2563eb; }
                    .count { color: #64748b; margin-bottom: 22px; }
                    table {
                        width: 100%;
                        border-collapse: collapse;
                        min-width: 650px;
                    }
                    th, td {
                        padding: 14px;
                        text-align: left;
                        border-bottom: 1px solid #e2e8f0;
                    }
                    th { background: #2563eb; color: white; }
                    tr:hover td { background: #f8fafc; }
                    .button {
                        display: inline-block;
                        margin-top: 22px;
                        padding: 12px 18px;
                        background: #2563eb;
                        color: white;
                        text-decoration: none;
                        border-radius: 7px;
                    }
                    .empty {
                        padding: 20px;
                        background: #f8fafc;
                        border-radius: 8px;
                    }
                </style>
            </head>
            <body>
            <div class="container">
                <h1>Student Records</h1>
            """);

        html.append("<p class='count'>Total students: ")
                .append(students.size())
                .append("</p>");

        if (students.isEmpty()) {
            html.append("<p class='empty'>No students registered yet.</p>");
        } else {
            html.append("""
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Department</th>
                        </tr>
                    </thead>
                    <tbody>
                """);

            for (Student student : students) {
                html.append("<tr>")
                        .append("<td>").append(student.getId()).append("</td>")
                        .append("<td>").append(escape(student.getName())).append("</td>")
                        .append("<td>").append(escape(student.getEmail())).append("</td>")
                        .append("<td>").append(escape(student.getPhone())).append("</td>")
                        .append("<td>").append(escape(student.getDepartment())).append("</td>")
                        .append("</tr>");
            }

            html.append("</tbody></table>");
        }

        html.append("""
                <a class="button" href="/">Register Another Student</a>
            </div>
            </body>
            </html>
            """);

        return html.toString();
    }

    private String escape(String value) {
        return value == null
                ? ""
                : HtmlUtils.htmlEscape(value);
    }
}
