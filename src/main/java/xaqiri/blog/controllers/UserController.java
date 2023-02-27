package xaqiri.blog.controllers;

import java.util.HashMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xaqiri.blog.UserRepository;
import xaqiri.blog.models.User;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UserController {
    /*
     * Routes:
     * GET /user: return all users
     * GET /user/name: return a single user
     * GET /user/posts: return all of a user's posts
     * POST /user/new: create a new user
     * PUT /user/name: edit a user
     * DELETE /user/name: delete a user
     */

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public @ResponseBody Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{name}")
    public @ResponseBody User signIn(@PathVariable String name) {
        User u = userRepository.findByUsername(name);
        return u;
    }

    @PostMapping("/user/add")
    public @ResponseBody String createUser(@RequestBody HashMap<String, String> user) {
        User n = new User();
        n.setName(user.get("username"));
        n.setPassword(user.get("password"));
        userRepository.save(n);
        System.out.println(n);
        return "User created";
    }

}
