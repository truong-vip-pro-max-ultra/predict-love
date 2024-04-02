package com.truongjae.predictlove.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Cookies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "longtext")
    private String cookie;

    @Column
    private Boolean status;
}
