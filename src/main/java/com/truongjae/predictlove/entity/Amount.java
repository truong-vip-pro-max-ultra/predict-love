package com.truongjae.predictlove.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Amount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createdDate;

    @Column
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
