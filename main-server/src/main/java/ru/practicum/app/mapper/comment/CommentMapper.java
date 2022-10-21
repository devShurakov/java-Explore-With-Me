package ru.practicum.app.mapper.comment;

import ru.practicum.app.dto.comment.CommentInDto;
import ru.practicum.app.dto.comment.CommentOutDto;
import ru.practicum.app.model.comment.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static Comment mapToComment(CommentInDto commentInDto) {
        Comment comment = new Comment();
        comment.setText(commentInDto.getText());
        comment.setCreated(LocalDateTime.now());
        return comment;
    }


    public static CommentOutDto mapToCommentOutDto(Comment comment) {

        CommentOutDto commentOutDto = new CommentOutDto();
        commentOutDto.setId(comment.getId());
        commentOutDto.setText(comment.getText());
        commentOutDto.setEventId(comment.getEvent().getEventId());
        return commentOutDto;
    }

    public static List<CommentOutDto> mapAlltoOutDto(List<Comment> list) {
        List<CommentOutDto> listToReturn = new ArrayList<>();
        for (Comment comment : list) {
            listToReturn.add(mapToCommentOutDto(comment));
        }
        return listToReturn;
    }
}
