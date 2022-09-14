package ru.practicum.app.event;

import lombok.Builder;
import ru.practicum.app.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.app.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    Integer eventId;

    @Column(name = "annotation", nullable = false)
    String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    Category category;

    @Column(name = "confirmed_requests")
//    @Column(name = "confirmed_requests", nullable = false)
    Integer confirmedRequests;

    @Column(name = "created_on", nullable = false)
    LocalDateTime created;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "event_date", nullable = false)
    LocalDateTime eventDate;

    @OneToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    User initiator;

    @Column(name = "location_lat", nullable = false)
    Float lat;

    @Column(name = "location_lon", nullable = false)
    Float lon;


    @Column(name = "paid", nullable = false)
    Boolean paid;

    @Column(name = "participant_limit", nullable = false)
    Integer participantLimit;

    @Column(name = "published_on", nullable = false)
    LocalDateTime publishedOn;

    @Column(name = "request_moderation", nullable = false)
    Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_status", nullable = false)
    EventStatus status;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "views", nullable = false)
    Integer views;

}
