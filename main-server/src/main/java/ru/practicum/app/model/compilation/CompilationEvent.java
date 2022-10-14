package ru.practicum.app.model.compilation;


import lombok.*;

import javax.persistence.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compilation_event")
public class CompilationEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "compilation_id")
    private Integer compilation;

    @Column(name = "event_id")
    private Integer event;

}
