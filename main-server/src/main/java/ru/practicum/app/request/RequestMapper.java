package ru.practicum.app.request;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestMapper {
    public static ParticipationRequestDto mapToParticipationRequestDto(Request cancelledRequest) {
        return new ParticipationRequestDto(
                cancelledRequest.getId(),
                cancelledRequest.getCreated(),
                cancelledRequest.getEvent().getEventId(),
                cancelledRequest.getRequester().getId(),
                cancelledRequest.getStatus().toString()
        );
    }

    public static RequestDto mapToRequestDto(Request request) {
        return new RequestDto(request.getId(),
                request.getEvent().getEventId(),
                request.getRequester().getId(),
                request.getStatus(),
                request.getCreated());
    }

    public List<RequestDto> mappAlltoRequestDto(List<Request> requestList) {
        List<RequestDto> returnList = new ArrayList<>();
        for (Request requests : requestList) {
            returnList.add(mapToRequestDto(requests));
        }
        return returnList;
    }

}
