package net.javaguides.springboorblogwebapp.service.impl;

import net.javaguides.springboorblogwebapp.mapper.PostMapper;
import net.javaguides.springboorblogwebapp.repository.PostRepository;
import net.javaguides.springboorblogwebapp.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private PostMapper postMapper;
    private PostService postService;

    @BeforeEach
    void setUp() {
        postService = new PostServiceImpl(postRepository, postMapper);
    }

    @Test
    void canFindAllPosts() {
        postService.findAllPosts();
        verify(postRepository).findAll();
    }
}