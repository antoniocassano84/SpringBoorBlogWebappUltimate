package net.javaguides.springboorblogwebapp.mapper;

import net.javaguides.springboorblogwebapp.dto.PostDto;
import net.javaguides.springboorblogwebapp.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDto mapToPostDto(Post post);
    Post mapToPost(PostDto postDto);

}
