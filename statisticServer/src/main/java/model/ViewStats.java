package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/***
 * запись информации о том, что был обработан запрос к эндпоинту API;
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "stats")
public class ViewStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "app", nullable = false)
    String app;

    @Column(name = "uri", nullable = false)
    String uri;

    @Column(name = "timestamp", nullable = false)
    Integer timestamp;

}
