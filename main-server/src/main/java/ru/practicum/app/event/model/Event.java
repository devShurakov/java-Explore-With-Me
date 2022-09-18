package ru.practicum.app.event.model;

import ru.practicum.app.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.app.request.model.Request;
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
    @Column(name = "event_id")
    Integer id;

    @Column(name = "annotation", nullable = false)
    String annotation;

    //    @JsonDeserialize(using = SubCategoryDeserializer.class)
    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "category_id", nullable = false) // TODO: 17.09.2022  category_id
    Category category;

    @Column(name = "confirmed_requests", nullable = false)
//    @ManyToOne
//    @JoinColumn(name = "id", referencedColumnName = "confirmedRequests_шв", nullable = false) // TODO: 17.09.2022  исправить
    Integer confirmedRequests;

    @Column(name = "created_on", nullable = false)
    LocalDateTime created;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "event_date", nullable = false)
    LocalDateTime eventDate;

    @OneToOne
    @JoinColumn(name = "initiator_id", nullable = false) // TODO: 17.09.2022  initiator_id
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
