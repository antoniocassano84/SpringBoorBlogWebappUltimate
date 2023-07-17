package net.javaguides.springboorblogwebapp.controller;

import net.javaguides.springboorblogwebapp.dto.PostDto;
import net.javaguides.springboorblogwebapp.service.CommentService;
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

import static net.javaguides.springboorblogwebapp.controller.PostController.*;
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
    private CommentService commentService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private PostDto postDto;

    private PostController postController;

    @BeforeEach
    void setUp() {
        postController = new PostController(postService, commentService);
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
    void postCommentsReturnsCorrectView() {
        String postCommentsView = postController.postComments(model);
        assertThat(postCommentsView).isEqualTo(ADMIN_COMMENTS);
    }

    @Test
    void deleteCommentsReturnsCorrectView() {
        String deleteCommentsView = postController.deleteComment(1L);
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(commentService).deleteComment(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(1L);
        assertThat(deleteCommentsView).isEqualTo(REDIRECT_ADMIN_POSTS_COMMENTS);
    }

    @Test
    void createPostsRedirectsToPostsAfterSuccessfullyAddedNewPost() {
        String postsView = postController.createPost(postDto, bindingResult, model);
        ArgumentCaptor<PostDto> postArgumentCaptor = ArgumentCaptor.forClass(PostDto.class);
        verify(postService).createPost(postArgumentCaptor.capture());
        assertThat(postArgumentCaptor.getValue()).isEqualTo(postDto);
        assertThat(postsView).isEqualTo(REDIRECT_ADMIN_POSTS);
    }

    @Test
    void createPostsReturnsToNewPostAfterInvalidForm() {
        given(bindingResult.hasErrors()).willReturn(true);
        String postsView = postController.createPost(postDto, bindingResult, model);
        verify(postService, never()).createPost(any());
        assertThat(postsView).isEqualTo(ADMIN_NEW_POST);
    }

    @Test
    void editPostFormReturnsCorrectView() {
        String editPostView = postController.editPostForm(1L, model);
        assertThat(editPostView).isEqualTo(ADMIN_EDIT_POST);
    }

    @Test
    void updatePostRedirectsViewNoError() {
        String redirectView = postController.updatePost(1L, postDto, bindingResult, model);
        ArgumentCaptor<PostDto> postArgumentCaptor = ArgumentCaptor.forClass(PostDto.class);
        verify(postService).updatePost(postArgumentCaptor.capture());
        assertThat(postArgumentCaptor.getValue()).isEqualTo(postDto);
        assertThat(redirectView).isEqualTo(REDIRECT_ADMIN_POSTS);
    }

    @Test
    void updatePostReturnsError() {
        given(bindingResult.hasErrors()).willReturn(true);
        String redirectView = postController.updatePost(1L, postDto, bindingResult, model);
        verify(postService, never()).updatePost(any());
        assertThat(redirectView).isEqualTo(ADMIN_EDIT_POST);
    }

    @Test
    void deletePostRedirectsView() {
        String redirectView = postController.deletePost(1L);
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(postService).deletePost(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(1L);
        assertThat(redirectView).isEqualTo(REDIRECT_ADMIN_POSTS);
    }

    @Test
    void viewPostReturnsCorrectView() {
        String returnedView = postController.viewPost("url", model);
        ArgumentCaptor<String> strArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(postService).findPostByUrl(strArgumentCaptor.capture());
        assertThat(strArgumentCaptor.getValue()).isEqualTo("url");
        assertThat(returnedView).isEqualTo(VIEW_POST);
    }

    @Test
    void searchPostReturnsCorrectView() {
        String searchPostView = postController.searchPosts("query", model);
        ArgumentCaptor<String> strArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(postService).searchPosts(strArgumentCaptor.capture());
        assertThat(strArgumentCaptor.getValue()).isEqualTo("query");
        assertThat(searchPostView).isEqualTo(ADMIN_POSTS);
    }
}