package event.model;

import category.model.Category;
import location.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.model.User;

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
    @JoinColumn(name = "id", referencedColumnName = "category_id")
    Category category;

    @Column(name = "confirmed_requests", nullable = false)
    Integer confirmed_requests;

    @Column(name = "created_on", nullable = false)
    LocalDateTime created;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "event_date", nullable = false)
    LocalDateTime eventDate;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "initiator_id")
    User initiator;

    @OneToOne
    @JoinColumn(name = "lat", referencedColumnName = "lat")
    @JoinColumn(name = "lon", referencedColumnName = "lon")
    Location location;

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
