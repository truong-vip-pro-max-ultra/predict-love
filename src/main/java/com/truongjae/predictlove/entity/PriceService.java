package com.truongjae.predictlove.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PriceService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long price;
}
