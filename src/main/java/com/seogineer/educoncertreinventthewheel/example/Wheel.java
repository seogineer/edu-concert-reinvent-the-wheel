package com.seogineer.educoncertreinventthewheel.example;

import javax.persistence.*;

@Entity
@Table(name = "wheel_info")
public class Wheel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 1L;

    @Column
    private String name = "Wheel";

    @Column
    private Integer size = 39;

    @Column
    private Integer price = 25000;

    @Transient
    private String data = "Make by Gyeong.D.Seo";
}