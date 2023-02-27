package xaqiri.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import xaqiri.blog.PostRepository;
import xaqiri.blog.UserRepository;
import xaqiri.blog.models.Post;
import xaqiri.blog.models.User;
import java.util.HashMap;

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

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/post")
    public @ResponseBody Iterable<Post> getPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/post/{id}")
    public @ResponseBody Optional<Post> getSinglePost(@PathVariable Integer id) {
        Post p = new Post();
        Optional<Post> post = postRepository.findById(id);
        return post;
    }

    @PostMapping("/post/new")
    public @ResponseBody String newPost(@RequestBody HashMap<String, String> post) {
        /*
         * post = {
         * postId: Integer,
         * postTitle: String,
         * postBody: Text,
         * userName: String
         * }
         */
        Post p = new Post();
        p.setPostBody(post.get("postBody"));
        p.setPostTitle(post.get("postTitle"));
        Optional<User> user = userRepository.findById(Integer.parseInt(post.get("userId")));
        if (user != null) {
            p.setUserId(user.get().getId());
            postRepository.save(p);
            return "Post created";
        } else {
            return "Post could not be created";
        }
    }

    @PutMapping("/post/edit/{id}")
    public @ResponseBody String editPost(@RequestBody HashMap<String, String> post, @PathVariable Integer id) {
        if (postRepository.existsById(id)) {
            Post p = postRepository.findById(id).get();
            if (post.containsKey("postTitle")) {
                p.setPostTitle(post.get("postTitle"));
            }
            if (post.containsKey("postBody")) {
                p.setPostBody(post.get("postBody"));
            }
            postRepository.save(p);
            return "Post edited";
        }
        return "Post not found";
    }

}
