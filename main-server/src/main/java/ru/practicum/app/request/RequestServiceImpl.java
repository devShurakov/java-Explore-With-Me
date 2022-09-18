package ru.practicum.app.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.app.event.EventRepository;
import ru.practicum.app.event.model.Event;
import ru.practicum.app.request.dto.ParticipationRequestDto;
import ru.practicum.app.request.model.Request;
import ru.practicum.app.request.model.RequestStatus;
import ru.practicum.app.user.User;
import ru.practicum.app.user.UserCastomException;
import ru.practicum.app.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RequestServiceImpl {

    private final RequestRepository requestRepository;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    private final RequestMapper requestMapper;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository,
                              RequestMapper requestMapper,
                              EventRepository eventRepository,
                              UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public ParticipationRequestDto create(int userId, int eventId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserCastomException("пользователь не найден"));
        Event event = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new UserCastomException("событие не найдено"));

        if (event.getStatus().name().equals(RequestStatus.APPROVED.name()) && event.getStatus().name().equals(RequestStatus.CANCELED.name())) {
            throw new RequestCustomException("подтвержденное или отмененное событие не может быть подтверждено");
        }

        Request request = new Request();
        request.setRequester(user);
        request.setEvent(event);
        request.setCreated(LocalDateTime.now());
        request.setStatus(RequestStatus.PENDING);
        log.info("создан запрос");
        return new ParticipationRequestDto();
    }

    public ParticipationRequestDto cancelRequest(ParticipationRequestDto participationRequestDto) {
        return new ParticipationRequestDto();
    }

    public List<ParticipationRequestDto> getRequest(int userId) {
        return new ArrayList<>();
    }
}
