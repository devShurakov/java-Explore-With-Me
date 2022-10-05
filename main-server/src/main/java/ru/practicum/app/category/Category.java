package ru.practicum.app.category;

import lombok.*;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // TODO: 17.09.2022 вернуть  и исправить в схеме
    @Column(name = "category_id", updatable = false, unique = true)
    private Integer category_id;

    @Column(name = "name", nullable = false)
    private String name;
}

