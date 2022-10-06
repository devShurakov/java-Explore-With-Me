package ru.practicum.app.category;

import lombok.*;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", updatable = false, unique = true)
    private Integer categoryId;

    @Column(name = "name", nullable = false)
    private String name;
}

