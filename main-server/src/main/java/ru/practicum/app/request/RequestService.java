package ru.practicum.app.request;

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
