package com.truongjae.predictlove.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createdDate;
    @Column(unique = true,columnDefinition = "varchar(15)",updatable = false)
    private String username;
    @Column(columnDefinition = "nvarchar(255)")
    private String password;

    @Column
    private Long accountBalance;

    @OneToMany(mappedBy = "user")
    private List<Amount> amountList;
}
