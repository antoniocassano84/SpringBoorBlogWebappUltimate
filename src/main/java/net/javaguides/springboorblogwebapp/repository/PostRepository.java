package net.javaguides.springboorblogwebapp.repository;

import jakarta.transaction.Transactional;
import net.javaguides.springboorblogwebapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Transactional
    Optional<Post> findByUrl(String url);
}
