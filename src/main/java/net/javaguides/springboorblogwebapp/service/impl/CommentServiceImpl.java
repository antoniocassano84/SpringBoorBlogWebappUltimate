package net.javaguides.springboorblogwebapp.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboorblogwebapp.dto.CommentDto;
import net.javaguides.springboorblogwebapp.entity.Comment;
import net.javaguides.springboorblogwebapp.entity.Post;
import net.javaguides.springboorblogwebapp.mapper.CommentMapper;
import net.javaguides.springboorblogwebapp.repository.CommentRepository;
import net.javaguides.springboorblogwebapp.repository.PostRepository;
import net.javaguides.springboorblogwebapp.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;
    CommentMapper commentMapper;
    PostRepository postRepository;
    @Override
    @Transactional
    public void createComment(String postUrl, CommentDto commentDto) {
        Comment comment = commentMapper.mapToComment(commentDto);
        Optional<Post> optPost = postRepository.findByUrl(postUrl);
        if(optPost.isPresent()) {
            comment.setPost(optPost.get());
            commentRepository.save(comment);
        }
    }

    @Override
    public List<CommentDto> findALlComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::mapToCommentDto).toList();
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
