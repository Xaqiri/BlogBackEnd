package xaqiri.blog.controllers;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xaqiri.blog.UserRepository;
import xaqiri.blog.models.User;
import xaqiri.blog.models.Post;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

// TODO: Add endpoint to get user's posts
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UserController {
    /*
     * Routes:
     * GET /user: return all users
     * GET /user/name: return a single user
     * GET /user/posts: return all of a user's posts
     * POST /user: create/edit a user
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
        return userRepository.findByUsername(name);
    }

    @GetMapping("/users/{id}/posts")
    public @ResponseBody ArrayList<Post> getPosts(@PathVariable Integer id) {
        ArrayList<Post> posts = new ArrayList<>();
        return posts;
    }

    @PostMapping("/users")
    public @ResponseBody User createUser(@RequestBody User user) {
        User n = new User();
        if (user.getName() == null || user.getPassword() == null || user.getName().equals("")) {
            return n;
        } else if (user.getId() != null) {
            Optional<User> checkUser = userRepository.findById(user.getId());
            if (checkUser.isPresent()) {
                n = checkUser.get();
            }
        } else {
            User checkUser = userRepository.findByUsername(user.getName());
            if (checkUser != null) {
                n = checkUser;
            }
        }
        n.setName(user.getName());
        n.setPassword(user.getPassword());
        userRepository.save(n);
        return n;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
    }

}
