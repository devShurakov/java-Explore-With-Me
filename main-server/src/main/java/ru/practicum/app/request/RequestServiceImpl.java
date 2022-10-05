package ru.practicum.app.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.app.event.EventRepository;
import ru.practicum.app.event.OperationException;
import ru.practicum.app.event.Event;
import ru.practicum.app.user.User;
import ru.practicum.app.user.UserCastomException;
import ru.practicum.app.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ParticipationRequestDto> getRequest(Integer userId) {
        return requestRepository.findAllByRequester(userId)
                .stream()
                .map(RequestMapper::mapToParticipationRequestDto)
                .collect(Collectors.toList());
    }

    public ParticipationRequestDto cancelRequest(Integer userId, Integer requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(); //// TODO: 03.10.2022 бросить исключение
        if (!userId.equals(request.getRequester().getId())) {
            String message = "Только создатель может отменить запрос";
            log.warn("ForbiddenOperationException at RequestServiceImpl.cancelRequest: {}", message);
            throw new OperationException(message);
        }
        request.setStatus(RequestStatus.CANCELED);
        Request cancelledRequest = requestRepository.save(request);
        log.info("RequestServiceImpl.cancelRequest: request {} successfully cancelled", request.getId());
        return requestMapper.mapToParticipationRequestDto(cancelledRequest);
    }
}
