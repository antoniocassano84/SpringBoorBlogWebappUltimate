package net.javaguides.springboorblogwebapp.service.impl;

import net.javaguides.springboorblogwebapp.dto.CommentDto;
import net.javaguides.springboorblogwebapp.entity.Comment;
import net.javaguides.springboorblogwebapp.entity.Post;
import net.javaguides.springboorblogwebapp.mapper.CommentMapper;
import net.javaguides.springboorblogwebapp.repository.CommentRepository;
import net.javaguides.springboorblogwebapp.repository.PostRepository;
import net.javaguides.springboorblogwebapp.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentMapper commentMapper;
    private CommentService commentService;
    private CommentDto commentDto;

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceImpl(commentRepository, commentMapper, postRepository);
        commentDto = CommentDto.builder().name("test").email("test").content("test").build();
    }

    @Test
    void canFindAllComments() {
        commentService.findALlComments();
        verify(commentRepository).findAll();

    }

    @Test
    void canCreateComment() {
        given(commentMapper.mapToComment(commentDto)).willReturn(Comment.builder().build());
        given(postRepository.findByUrl(anyString())).willReturn(Optional
                .of(Post.builder().title("title").shortDescription("desc").content("content").build()));

        commentService.createComment("url", commentDto);
        ArgumentCaptor<String> strArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(postRepository).findByUrl(strArgumentCaptor.capture());
        assertThat(strArgumentCaptor.getValue()).isEqualTo("url");

        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(commentArgumentCaptor.capture());
        assertThat(commentArgumentCaptor.getValue()).isEqualTo(commentMapper.mapToComment(commentDto));
    }

    @Test
    void canDeleteComment() {
        commentService.deleteComment(1L);
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(commentRepository).deleteById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(1L);
    }

}