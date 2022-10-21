package ru.practicum.app.service.comment;

import ru.practicum.app.dto.comment.CommentInDto;
import ru.practicum.app.dto.comment.CommentOutDto;
import ru.practicum.app.dto.comment.CommentUpdateDto;

import java.util.List;

public interface CommentService {
    CommentOutDto createComment(CommentInDto commentInDto, Integer userId, Integer eventId);

    List<CommentOutDto> getUserComments(Integer userId);

    CommentOutDto updateComment(CommentUpdateDto commentUpdateDto, Integer commentId);

    void deleteComment(int commentId, int userId);

    void deleteCommentByAdmin(Integer commentId);
}
