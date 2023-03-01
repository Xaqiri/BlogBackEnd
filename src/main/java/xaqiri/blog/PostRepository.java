package xaqiri.blog;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

import xaqiri.blog.models.Post;

public interface PostRepository extends CrudRepository<Post, Integer> {
  List<Post> findAll();
}
