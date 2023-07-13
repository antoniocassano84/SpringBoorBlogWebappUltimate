package net.javaguides.springboorblogwebapp.service;

import net.javaguides.springboorblogwebapp.dto.PostDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    List<PostDto> findAllPosts();

    void createPost(PostDto postDto);
    Page<PostDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    PostDto findPostById(Long postId);
    void updatePost(PostDto postDto);
    void deletePost(Long postId);

}
