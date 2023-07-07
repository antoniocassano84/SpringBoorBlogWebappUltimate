package net.javaguides.springboorblogwebapp.controller;

import net.javaguides.springboorblogwebapp.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {
    @Mock
    private PostService postService;

    @Mock
    private Model model;

    private PostController postController;

    @BeforeEach
    void setUp() {
        postController = new PostController(postService);
    }

    @Test
    void postsReturnsCorrectView() {
        String postsView = postController.posts(model);
        verify(postService).findAllPosts();
        assertThat(postsView).isEqualTo(PostController.ADMIN_POSTS);
    }

    @Test
    void newPostReturnsCorrectView() {
        String newPostView = postController.newPost(model);
        assertThat(newPostView).isEqualTo(PostController.ADMIN_NEW_POST_VIEW);
    }
}