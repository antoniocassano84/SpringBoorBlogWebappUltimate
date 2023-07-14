package net.javaguides.springboorblogwebapp.service.impl;

import net.javaguides.springboorblogwebapp.dto.PostDto;
import net.javaguides.springboorblogwebapp.mapper.PostMapper;
import net.javaguides.springboorblogwebapp.repository.PostRepository;
import net.javaguides.springboorblogwebapp.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<PostDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                        Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        return postRepository.findAll(PageRequest.of(pageNo, pageSize, sort)).map(postMapper::mapToPostDto);
    }

    @Override
    public PostDto findPostById(Long postId) {
        return postRepository.findById(postId).map(postMapper::mapToPostDto).orElse(null);
    }

    @Override
    public void updatePost(PostDto postDto) {
        postRepository.save(postMapper.mapToPost(postDto));
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    @Transactional
    public PostDto findPostByUrl(String postUrl) {
        return postRepository.findByUrl(postUrl).map(postMapper::mapToPostDto).orElse(null);
    }

    @Override
    @Transactional
    public List<PostDto> searchPosts(String query) {
        return postRepository.searchPosts(query).stream().map(postMapper::mapToPostDto).toList();
    }
}
