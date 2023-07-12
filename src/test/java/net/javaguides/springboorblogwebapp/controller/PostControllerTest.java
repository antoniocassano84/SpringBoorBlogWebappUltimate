package net.javaguides.springboorblogwebapp.controller;

import net.javaguides.springboorblogwebapp.dto.PostDto;
import net.javaguides.springboorblogwebapp.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

import static net.javaguides.springboorblogwebapp.controller.PostController.ADMIN_NEW_POST;
import static net.javaguides.springboorblogwebapp.controller.PostController.ADMIN_POSTS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {
    @Mock
    private PostService postService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private PostDto postDto;

    private PostController postController;

    @BeforeEach
    void setUp() {
        postController = new PostController(postService);
        postDto = PostDto.builder().title("test").content("test").build();
    }

    @Test
    void postsReturnsCorrectView() {
        given(postService.findPaginated(anyInt(), anyInt(), anyString(), anyString()))
                .willReturn(new PageImpl<>(List.of(PostDto.builder().build(), PostDto.builder().build())));
        String postsView = postController.posts(model);
        assertThat(postsView).isEqualTo(ADMIN_POSTS);
    }

    @Test
    void newPostReturnsCorrectView() {
        String newPostView = postController.newPost(model);
        assertThat(newPostView).isEqualTo(ADMIN_NEW_POST);
    }

    @Test
    void createPostsRedirectsToPostsAfterSuccessfullyAddedNewPost() {
        String postsView = postController.createPost(postDto, bindingResult, model);
        ArgumentCaptor<PostDto> postArgumentCaptor = ArgumentCaptor.forClass(PostDto.class);
        verify(postService).createPost(postArgumentCaptor.capture());
        assertThat(postArgumentCaptor.getValue()).isEqualTo(postDto);
        assertThat(postsView).isEqualTo("redirect:" + ADMIN_POSTS);
    }

    @Test
    void createPostsReturnsToNewPostAfterInvalidForm() {
        given(bindingResult.hasErrors()).willReturn(true);
        String postsView = postController.createPost(postDto, bindingResult, model);
        verify(postService, never()).createPost(any());
        assertThat(postsView).isEqualTo(ADMIN_NEW_POST);
    }
}