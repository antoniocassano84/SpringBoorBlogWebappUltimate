package net.javaguides.springboorblogwebapp.controller;

import net.javaguides.springboorblogwebapp.dto.CommentDto;
import net.javaguides.springboorblogwebapp.service.CommentService;
import net.javaguides.springboorblogwebapp.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
    @Mock
    private PostService postService;

    @Mock
    private CommentService commentService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private CommentDto commentDto;

    private CommentController commentController;

    @BeforeEach
    void setUp() {
        commentController = new CommentController(commentService, postService);
        commentDto = CommentDto.builder().name("test").email("test").content("test").build();
    }


    @Test
    void createCommentRedirectsToCommentsAfterSuccessfullyAddedNewComment() {
        String view = commentController.createComment(commentDto, bindingResult, model, "url");
        ArgumentCaptor<CommentDto> commentArgumentCaptor = ArgumentCaptor.forClass(CommentDto.class);
        ArgumentCaptor<String> strArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(commentService).createComment(strArgumentCaptor.capture(), commentArgumentCaptor.capture());
        assertThat(strArgumentCaptor.getValue()).isEqualTo("url");
        assertThat(commentArgumentCaptor.getValue()).isEqualTo(commentDto);
        assertThat(view).isEqualTo("redirect:/post/url");
    }

    @Test
    void createCommentRedirectsToCommentsAfterInvalidAddedNewComment() {
        given(bindingResult.hasErrors()).willReturn(true);
        String view = commentController.createComment(commentDto, bindingResult, model, "url");
        verify(commentService, never()).createComment(anyString(), any(CommentDto.class));
        assertThat(view).isEqualTo("blog/blog_post");
    }
}