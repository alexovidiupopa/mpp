package ro.ubb.catalog.core.model;


import lombok.*;
import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class Sensor {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;
    @Column(name = "measurement")
    private double measurement;
    @Column(name = "time")
    private long time;

}
