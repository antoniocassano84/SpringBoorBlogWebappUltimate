package net.javaguides.springboorblogwebapp.repository;

import net.javaguides.springboorblogwebapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByUrl(String url);
}
