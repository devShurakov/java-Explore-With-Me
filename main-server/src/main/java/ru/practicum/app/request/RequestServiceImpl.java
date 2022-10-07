package ru.practicum.app.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.app.event.EventRepository;
import ru.practicum.app.exception.OperationException;
import ru.practicum.app.event.Event;
import ru.practicum.app.exception.RequestCustomException;
import ru.practicum.app.user.User;
import ru.practicum.app.exception.UserCastomException;
import ru.practicum.app.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
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
        return requestMapper.mapToParticipationRequestDto(requestRepository.save(request));
    }

    public Collection<RequestDto> getRequest(Integer userId) {

        List<Request> requestList = requestRepository.findAllByRequesterId(userId);
        Collection<RequestDto> listToReturn = requestMapper.mappAlltoRequestDto(requestList);
        return listToReturn;
    }

    public ParticipationRequestDto cancelRequest(Integer userId, Integer requestId) {
        userRepository.findById(userId).orElseThrow(() -> {
            throw new RequestCustomException("пользователь не найдена");
        });
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> {
                    throw new RequestCustomException("заявка не найдена");
                });
        if (!userId.equals(request.getRequester().getId())) {
            String message = "Только создатель может отменить запрос";
            throw new OperationException(message);
        }

        request.setStatus(RequestStatus.CANCELED);
        Request cancelledRequest = requestRepository.save(request);
        return requestMapper.mapToParticipationRequestDto(cancelledRequest);
    }

    public ParticipationRequestDto acceptRequestByUser(Integer userId, Integer eventId, Integer reqId) {
        Request req = requestRepository.findById(reqId).orElseThrow();
        req.setStatus(RequestStatus.APPROVED);
        requestRepository.save(req); //todo
        return requestMapper.mapToParticipationRequestDto(req);
    }

    public void rejectRequestByUser(Integer userId, Integer eventId, Integer reqId) {

        requestRepository.setStateById("REJECTED", reqId);
    }

    public List<RequestDto> getRequestByUser(Integer userId, Integer eventId) {

        List<Request> requests = requestRepository.getRequestByUser(userId, eventId);

        return requests.stream().map(RequestMapper::mapToRequestDto).collect(Collectors.toList());
    }
}
