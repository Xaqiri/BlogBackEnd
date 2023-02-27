package xaqiri.blog.controllers;

import java.util.HashMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xaqiri.blog.UserRepository;
import xaqiri.blog.models.User;

import org.springframework.web.bind.annotation.*;

// TODO: Add error handling
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

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public @ResponseBody Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{name}")
    public @ResponseBody User signIn(@PathVariable String name) {
        User u = userRepository.findByUsername(name);
        return u;
    }

    @PostMapping("/users")
    public @ResponseBody User createUser(@RequestBody User user) {
        User n = new User();
        n.setName(user.getName());
        n.setPassword(user.getPassword());
        userRepository.save(n);
        return n;
    }

    @PutMapping("/users")
    public @ResponseBody User updateUser(@RequestBody User user) {
        User n = userRepository.findByUsername(user.getName());
        if (n != null) {
            n.setName(user.getName());
            n.setPassword(user.getPassword());
            userRepository.save(n);
        }
        return n;
    }

    @DeleteMapping("/users/{name}")
    public void deleteUser(@PathVariable String name) {
        User n = userRepository.findByUsername(name);
        if (n != null) {
            userRepository.deleteById(n.getId());
        }
    }

}
