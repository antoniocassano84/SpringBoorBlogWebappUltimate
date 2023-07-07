package net.javaguides.springboorblogwebapp.service.impl;

import net.javaguides.springboorblogwebapp.dto.PostDto;
import net.javaguides.springboorblogwebapp.mapper.PostMapper;
import net.javaguides.springboorblogwebapp.repository.PostRepository;
import net.javaguides.springboorblogwebapp.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    @Transactional
    public List<PostDto> findAllPosts() {
        return postRepository.findAll().stream().map(postMapper::mapToPostDto).toList();
    }

    @Override
    public void createPost(PostDto postDto) {
        postRepository.save(postMapper.mapToPost(postDto));
    }
}
