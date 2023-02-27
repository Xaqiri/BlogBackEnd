package xaqiri.blog;

import org.springframework.data.repository.CrudRepository;

import xaqiri.blog.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}