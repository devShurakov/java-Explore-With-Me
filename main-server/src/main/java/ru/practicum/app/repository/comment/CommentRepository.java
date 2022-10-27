package ru.practicum.app.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.app.model.comment.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByAuthor_Id(Integer userId);
}

