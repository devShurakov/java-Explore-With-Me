package ru.practicum.app.service.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.app.dto.comment.CommentInDto;
import ru.practicum.app.dto.comment.CommentOutDto;
import ru.practicum.app.dto.comment.CommentUpdateDto;
import ru.practicum.app.exception.CommentCustomException;
import ru.practicum.app.mapper.comment.CommentMapper;
import ru.practicum.app.model.comment.Comment;
import ru.practicum.app.model.event.Event;
import ru.practicum.app.model.user.User;
import ru.practicum.app.repository.comment.CommentRepository;
import ru.practicum.app.repository.event.EventRepository;
import ru.practicum.app.repository.user.UserRepository;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(EventRepository eventRepository,
                              UserRepository userRepository,
                              CommentRepository commentRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentOutDto createComment(CommentInDto commentInDto, Integer userId, Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        Comment comment = CommentMapper.mapToComment(commentInDto);
        comment.setAuthor(user);
        comment.setEvent(event);
        return CommentMapper.mapToCommentOutDto(commentRepository.save(comment));
    }


    @Override
    public List<CommentOutDto> getUserComments(Integer userId) {
        List<Comment> list = commentRepository.findByAuthor_Id(userId);
        return CommentMapper.mapAlltoOutDto(list);
    }

    @Override
    public CommentOutDto updateComment(CommentUpdateDto commentUpdateDto, Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setText(commentUpdateDto.getText());
        return CommentMapper.mapToCommentOutDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(int commentId, int userId) {
        userRepository.findById(userId).orElseThrow(() -> {
            throw new CommentCustomException("Событие не найдено");
        });
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (comment.getAuthor().getId() != userId) {
            throw new CommentCustomException("Можно удалить только свой комментарий");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public void deleteCommentByAdmin(Integer commentId) {
        if (commentId == null) {
            throw new CommentCustomException("Не корректно введенные данные");
        }
        commentRepository.deleteById(commentId);
    }
}
