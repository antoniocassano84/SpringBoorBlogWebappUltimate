package net.javaguides.springboorblogwebapp.controller;

import net.javaguides.springboorblogwebapp.dto.PostDto;
import net.javaguides.springboorblogwebapp.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static net.javaguides.springboorblogwebapp.controller.BlogController.BLOG_POST;
import static net.javaguides.springboorblogwebapp.controller.BlogController.BLOG_VIEW_POSTS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BlogControllerTest {

    @Mock
    private PostService postService;

    @Mock
    private Model model;

    private BlogController blogController;

    @BeforeEach
    void setUp() {
        blogController = new BlogController(postService);
    }

    @Test
    void canViewBlogPosts() {
        String view = blogController.viewBlogPosts(model);
        verify(postService).findAllPosts();
        assertThat(view).isEqualTo(BLOG_VIEW_POSTS);
    }

    @Test
    void canShowPost() {
        String view = blogController.showPost("url", model);
        ArgumentCaptor<String> strArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(postService).findPostByUrl(strArgumentCaptor.capture());
        assertThat(strArgumentCaptor.getValue()).isEqualTo("url");
        assertThat(view).isEqualTo(BLOG_POST);
    }

    @Test
    void canSearchPosts() {
        String view = blogController.searchPosts("query", model);
        ArgumentCaptor<String> strArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(postService).searchPosts(strArgumentCaptor.capture());
        assertThat(strArgumentCaptor.getValue()).isEqualTo("query");
        assertThat(view).isEqualTo(BLOG_VIEW_POSTS);
    }
}