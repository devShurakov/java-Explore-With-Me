package request.model;

import event.model.Event;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "created", nullable = false)
    LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "event_id")
    Event event;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "requester_id")
    User requester;

    @Enumerated(EnumType.STRING)
    @Column(name = "requests_status")
    RequestStatus status;

}
