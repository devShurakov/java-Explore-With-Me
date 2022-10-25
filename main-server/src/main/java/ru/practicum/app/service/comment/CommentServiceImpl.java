package ru.practicum.app.service.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.app.dto.comment.CommentNewDto;
import ru.practicum.app.dto.comment.CommentDto;
import ru.practicum.app.exception.CommentCustomException;
import ru.practicum.app.mapper.comment.CommentMapper;
import ru.practicum.app.model.comment.Comment;
import ru.practicum.app.model.event.Event;
import ru.practicum.app.model.user.User;
import ru.practicum.app.repository.comment.CommentRepository;
import ru.practicum.app.repository.event.EventRepository;
import ru.practicum.app.repository.user.UserRepository;

import java.time.LocalDateTime;
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
    public CommentDto createComment(CommentNewDto commentNewDto, Integer userId, Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        Comment comment = CommentMapper.mapToComment(commentNewDto);
        comment.setAuthor(user);
        comment.setEvent(event);
        comment.setCreated(LocalDateTime.now());
        return CommentMapper.mapToCommentOutDto(commentRepository.save(comment));
    }


    @Override
    public List<CommentDto> getUserComments(Integer userId) {
        List<Comment> list = commentRepository.findByAuthor_Id(userId);
        return CommentMapper.mapAlltoOutDto(list);
    }

    @Override
    public CommentDto updateComment(CommentNewDto commentUpdateDto, Integer commentId) {
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
