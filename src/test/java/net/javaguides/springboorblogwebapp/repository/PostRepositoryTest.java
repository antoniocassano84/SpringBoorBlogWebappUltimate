package net.javaguides.springboorblogwebapp.repository;

import net.javaguides.springboorblogwebapp.entity.Post;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository.saveAllAndFlush(List.of(
                Post.builder().title("test").content("test content").url("url test 1").build(),
                Post.builder().title("test").content("test content").url("url test 2").build(),
                Post.builder().title("test").content("test content").url("url test 3").build()
                )
        );
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    void itShouldFindExistingPostByUrl() {
        // when
        Optional<Post> postByUrl = postRepository.findByUrl("url test 1");
        // then
        assertThat(postByUrl).isPresent();
    }

    @Test
    void itShouldNotFindPostByUrl() {
        // when
        Optional<Post> postByUrl = postRepository.findByUrl("test");
        // then
        assertThat(postByUrl).isNotPresent();
    }
}