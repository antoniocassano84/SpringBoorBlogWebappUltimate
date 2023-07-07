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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
}