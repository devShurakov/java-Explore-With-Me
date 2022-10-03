package ru.practicum.app.compilation;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "compilations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private String title;

    private Boolean pinned;
}
