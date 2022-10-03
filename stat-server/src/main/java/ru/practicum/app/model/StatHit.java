package ru.practicum.app.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@RequiredArgsConstructor
@Entity(name = "StatHit")
@Table(name = "statistics")
public class StatHit {
    @Id
    @SequenceGenerator(name = "statistics_sequence", sequenceName = "statistics_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "statistics_sequence")
    @Column(name = "id", updatable = false, unique = true)
    private int id;
    @Column(name = "app")
    private String app;
    @Column(name = "uri")
    private String uri;
    @Column(name = "ip")
    private String ip;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
