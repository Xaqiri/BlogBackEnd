package xaqiri.blog.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UserController {

    private final ArrayList<String> names = new ArrayList<String>();

    @GetMapping("/user")
    public @ResponseBody ArrayList<String> user() {
        return names;
    }

    @PostMapping("/user")
    public @ResponseBody ArrayList<String> createUser(@RequestParam String name, @RequestParam String password) {
        this.names.add(name);
        return names;
    }
}
