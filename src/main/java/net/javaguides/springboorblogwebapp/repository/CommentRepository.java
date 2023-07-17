package net.javaguides.springboorblogwebapp.repository;

import net.javaguides.springboorblogwebapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
