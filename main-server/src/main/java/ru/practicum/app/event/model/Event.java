package ru.practicum.app.event.model;

import ru.practicum.app.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.app.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "annotation", nullable = false)
    String annotation;

    @OneToOne
    @JoinColumn(name = "category_id")
    Category category;

    @Column(name = "confirmed_requests", nullable = false)
    Integer confirmedRequests;

    @Column(name = "created_on", nullable = false)
    LocalDateTime created;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "eventDate", nullable = false)
    LocalDateTime eventDate;

    @OneToOne
    @JoinColumn(name = "initiator_id")
    User initiator;

    @Column(name = "location_lat")
    Float lat;

    @Column(name = "location_lon")
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
    @Column(name = "event_status")
    EventStatus status;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "views", nullable = false)
    Integer views;

}
