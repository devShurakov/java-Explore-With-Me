package ru.practicum.app.mapper.comment;

import ru.practicum.app.dto.comment.CommentNewDto;
import ru.practicum.app.dto.comment.CommentDto;
import ru.practicum.app.model.comment.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static Comment mapToComment(CommentNewDto commentNewDto) {
        Comment comment = new Comment();
        comment.setText(commentNewDto.getText());
        return comment;
    }


    public static CommentDto mapToCommentOutDto(Comment comment) {

        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setEventId(comment.getEvent().getEventId());
        return commentDto;
    }

    public static List<CommentDto> mapAlltoOutDto(List<Comment> list) {
        List<CommentDto> listToReturn = new ArrayList<>();
        for (Comment comment : list) {
            listToReturn.add(mapToCommentOutDto(comment));
        }
        return listToReturn;
    }
}
