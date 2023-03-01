package xaqiri.blog.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import xaqiri.blog.PostRepository;
import xaqiri.blog.UserRepository;
import xaqiri.blog.models.Post;
import xaqiri.blog.models.User;

// TODO: Add exception handling
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class PostController {
  /*
   * Routes:
   * GET /post: return all posts
   * GET /post/id: return a single post
   * GET /post/comments: return all of a post's comments
   * POST /post/new: create a new post
   * PUT /post/id: edit a post
   * DELETE /post/delete/id: delete a single post
   */

  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public PostController(PostRepository postRepository, UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  @GetMapping("/posts")
  public @ResponseBody Iterable<Post> getPosts() {
    return postRepository.findAll();
  }

  @GetMapping("/posts/{id}")
  public @ResponseBody Optional<Post> getSinglePost(@PathVariable Integer id) {
    return postRepository.findById(id);
  }

  @PostMapping("/posts")
  public @ResponseBody Post newPost(@RequestBody Post post) {
    /*
     * post = {
     * postId: Integer,
     * postTitle: String,
     * postBody: Text,
     * userName: String
     * }
     */
    Post p = new Post();
    if (post.getPostTitle() == null || post.getPostBody() == null || post.getUserId() == null) {
      return p;
    }
    p.setPostBody(post.getPostBody());
    p.setPostTitle(post.getPostTitle());
    Optional<User> user = userRepository.findById(post.getUserId());
    if (user.isPresent()) {
      p.setUserId(user.get().getId());
      postRepository.save(p);
    }
    return p;
  }

  @PutMapping("/posts/{id}")
  public @ResponseBody Post editPost(@RequestBody Post post, @PathVariable Integer id) {
    if (post.getUserId() == null || post.getPostBody() == null || post.getPostTitle() == null) {
      return new Post();
    }
    Optional<Post> maybePost = postRepository.findById(id);
    if (maybePost.isPresent()) {
      Post p = maybePost.get();
      p.setPostBody(post.getPostBody());
      p.setPostTitle(post.getPostTitle());
      p.setUserId(post.getUserId());
      postRepository.save(p);
      return p;
    }
    return new Post();
  }

  @DeleteMapping("/posts/{id}")
  public void deletePost(@PathVariable Integer id) {
    Optional<Post> p = postRepository.findById(id);
    p.ifPresent(postRepository::delete);
  }

}
