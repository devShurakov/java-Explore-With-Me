package ru.practicum.app.service.request;

import ru.practicum.app.dto.request.RequestDto;
import ru.practicum.app.dto.request.ParticipationRequestDto;

import java.util.Collection;
import java.util.List;

public interface RequestService {
    ParticipationRequestDto create(int userId, int eventId);

    Collection<RequestDto> getRequest(Integer userId);

    ParticipationRequestDto cancelRequest(Integer userId, Integer requestId);

    ParticipationRequestDto acceptRequestByUser(Integer userId, Integer eventId, Integer reqId);

    ParticipationRequestDto rejectRequestByUser(Integer userId, Integer eventId, Integer reqId);

    List<RequestDto> getRequestByUser(Integer userId, Integer eventId);
}
