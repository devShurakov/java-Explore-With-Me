package ru.practicum.app.service.comment;

import ru.practicum.app.dto.comment.CommentNewDto;
import ru.practicum.app.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentNewDto commentNewDto, Integer userId, Integer eventId);

    List<CommentDto> getUserComments(Integer userId);

    CommentDto updateComment(CommentNewDto commentUpdateDto, Integer commentId);

    void deleteComment(int commentId, int userId);

    void deleteCommentByAdmin(Integer commentId);
}
