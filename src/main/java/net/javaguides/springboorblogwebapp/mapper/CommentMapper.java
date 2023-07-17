package net.javaguides.springboorblogwebapp.mapper;

import net.javaguides.springboorblogwebapp.dto.CommentDto;
import net.javaguides.springboorblogwebapp.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto mapToCommentDto(Comment comment);
    Comment mapToComment(CommentDto commentDto);

}
