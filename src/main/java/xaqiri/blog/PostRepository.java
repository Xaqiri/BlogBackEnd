package xaqiri.blog;

import org.springframework.data.repository.CrudRepository;

import xaqiri.blog.models.Post;

public interface PostRepository extends CrudRepository<Post, Integer> {
}
