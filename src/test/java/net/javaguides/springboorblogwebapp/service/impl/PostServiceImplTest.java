package net.javaguides.springboorblogwebapp.service.impl;

import net.javaguides.springboorblogwebapp.dto.PostDto;
import net.javaguides.springboorblogwebapp.entity.Post;
import net.javaguides.springboorblogwebapp.mapper.PostMapper;
import net.javaguides.springboorblogwebapp.repository.PostRepository;
import net.javaguides.springboorblogwebapp.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private PostMapper postMapper;
    private PostService postService;
    private PostDto postDto;

    @BeforeEach
    void setUp() {
        postService = new PostServiceImpl(postRepository, postMapper);
        postDto = PostDto.builder().title("test").content("test").build();
    }

    @Test
    void canFindAllPosts() {
        postService.findAllPosts();
        verify(postRepository).findAll();
    }

    @Test
    void canAddPost() {
        // when
        postService.createPost(postDto);
        // then
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postArgumentCaptor.capture());
        assertThat(postArgumentCaptor.getValue()).isEqualTo(postMapper.mapToPost(postDto));
    }

    @Test
    void canFindPaginatedPostsDesc() {
        given(postRepository.findAll(any(PageRequest.class)))
                .willReturn(new PageImpl<>(List.of(Post.builder().build(), Post.builder().build())));

        postService.findPaginated(1,5, "title", Sort.Direction.DESC.name());

        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(postRepository).findAll(pageableArgumentCaptor.capture());
        assertThat(pageableArgumentCaptor.getValue())
                .isEqualTo(PageRequest.of(1, 5, Sort.by("title").descending()));
    }

    @Test
    void canFindPaginatedPostsAsc() {
        given(postRepository.findAll(any(PageRequest.class)))
                .willReturn(new PageImpl<>(List.of(Post.builder().build(), Post.builder().build())));

        postService.findPaginated(1,5, "title", Sort.Direction.ASC.name());

        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(postRepository).findAll(pageableArgumentCaptor.capture());
        assertThat(pageableArgumentCaptor.getValue())
                .isEqualTo(PageRequest.of(1, 5, Sort.by("title").ascending()));
    }

    @Test
    void canFindPostById() {
        postService.findPostById(1L);
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(postRepository).findById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(1L);
    }

    @Test
    void canUpdatePost() {
        postService.updatePost(postDto);
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postArgumentCaptor.capture());
        assertThat(postArgumentCaptor.getValue()).isEqualTo(postMapper.mapToPost(postDto));
    }

}