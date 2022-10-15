package ru.practicum.app.service.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.app.dto.request.RequestDto;
import ru.practicum.app.repository.event.EventRepository;
import ru.practicum.app.exception.OperationException;
import ru.practicum.app.model.event.Event;
import ru.practicum.app.exception.RequestCustomException;
import ru.practicum.app.model.request.Request;
import ru.practicum.app.repository.request.RequestRepository;
import ru.practicum.app.dto.request.ParticipationRequestDto;
import ru.practicum.app.mapper.request.RequestMapper;
import ru.practicum.app.model.request.RequestStatus;
import ru.practicum.app.model.user.User;
import ru.practicum.app.exception.UserCastomException;
import ru.practicum.app.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;


    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository,
                              EventRepository eventRepository,
                              UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
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
        return RequestMapper.mapToParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public Collection<RequestDto> getRequest(Integer userId) {

        List<Request> requestList = requestRepository.findAllByRequesterId(userId);
        Collection<RequestDto> listToReturn = RequestMapper.mappAlltoRequestDto(requestList);
        log.info("получен запрос");
        return listToReturn;
    }

    @Override
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
        log.info("создана отмена запроса");
        return RequestMapper.mapToParticipationRequestDto(cancelledRequest);
    }

    @Override
    public ParticipationRequestDto acceptRequestByUser(Integer userId, Integer eventId, Integer reqId) {
        Request req = requestRepository.findById(reqId).orElseThrow();
        req.setStatus(RequestStatus.CONFIRMED);
        requestRepository.save(req);
        log.info("запрос подтвержден");
        return RequestMapper.mapToParticipationRequestDto(req);
    }

    @Override
    public ParticipationRequestDto rejectRequestByUser(Integer userId, Integer eventId, Integer reqId) {
        Request req = requestRepository.findById(reqId).orElseThrow();
        req.setStatus(RequestStatus.REJECTED);
        requestRepository.save(req);
        log.info("запрос отменен");
        return RequestMapper.mapToParticipationRequestDto(req);
    }

    @Override
    public List<RequestDto> getRequestByUser(Integer userId, Integer eventId) {
        List<Request> requests = requestRepository.getRequestByUser(userId, eventId);
        log.info("запрос получен");
        return requests.stream().map(RequestMapper::mapToRequestDto).collect(Collectors.toList());
    }
}
