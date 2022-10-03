package ru.practicum.app.request;

import org.springframework.stereotype.Component;

@Component
public class RequestMapper {
    public static ParticipationRequestDto mapToParticipationRequestDto(Request cancelledRequest) {
        return new ParticipationRequestDto(
                cancelledRequest.getId(),
                cancelledRequest.getCreated(),
                cancelledRequest.getEvent().getId(),
                cancelledRequest.getRequester().getId(),
                cancelledRequest.getStatus().toString()
        );
    }

//    public List <ParticipationRequestDto> mapAllToParticipationRequestDto(Request request) {
//        List<ParticipationRequestDto> usersDtoList = new ArrayList<>();
//        for (Request req : request) {
//            usersDtoList.add(mapToParticipationRequestDto(req));
//        }
//        return usersDtoList;
//    }
}
