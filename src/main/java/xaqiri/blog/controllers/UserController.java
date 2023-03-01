package xaqiri.blog.controllers;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xaqiri.blog.UserRepository;
import xaqiri.blog.PostRepository;
import xaqiri.blog.models.User;
import xaqiri.blog.models.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

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
  private final PostRepository postRepository;

  public UserController(UserRepository userRepository, PostRepository postRepository) {
    this.userRepository = userRepository;
    this.postRepository = postRepository;
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
  public @ResponseBody List<Post> getPosts(@PathVariable Integer id) {
    return postRepository.findAll()
        .stream()
        .filter(p -> p.getUserId() == id)
        .collect(Collectors.toList());
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
