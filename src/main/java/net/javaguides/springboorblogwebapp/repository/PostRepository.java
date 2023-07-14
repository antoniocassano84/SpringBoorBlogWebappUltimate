package net.javaguides.springboorblogwebapp.repository;

import org.springframework.transaction.annotation.Transactional;
import net.javaguides.springboorblogwebapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Transactional
    Optional<Post> findByUrl(String url);

    @Query("SELECT p FROM Post p WHERE "
            + "p.title LIKE CONCAT('%', :query, '%') OR "
            + "p.shortDescription LIKE CONCAT('%', :query, '%')")
    List<Post> searchPosts(String query);
}
