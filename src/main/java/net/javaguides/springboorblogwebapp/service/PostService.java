package net.javaguides.springboorblogwebapp.service;

import net.javaguides.springboorblogwebapp.dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> findAllPosts();

    void createPost(PostDto postDto);

}
