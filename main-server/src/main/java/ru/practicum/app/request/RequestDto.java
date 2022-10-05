package ru.practicum.app.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.app.event.Event;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RequestDto {
    private Integer id;
    private Event event;
    private LocalDateTime created;
    private Integer requester;
//
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public class User {
//
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private Integer id;
//        @Column(name = "title")
//        private String title;
//        @Column(name = "pinned")
//        private Boolean pinned;
//    }

}