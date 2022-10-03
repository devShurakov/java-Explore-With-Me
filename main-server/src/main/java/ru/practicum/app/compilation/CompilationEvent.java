package ru.practicum.app.compilation;


import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "compilation_event")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompilationEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "compilation_id")
    private Integer compilation;

    @Column(name = "event_id")
    private Integer event;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "pinned", nullable = false)
    Boolean pinned;
}
