package net.javaguides.springboorblogwebapp.service;

import net.javaguides.springboorblogwebapp.dto.CommentDto;

import java.util.List;

public interface CommentService {

    void createComment(String postUrl, CommentDto commentDto);

    List<CommentDto> findALlComments();

    void deleteComment(Long commentId);
}
