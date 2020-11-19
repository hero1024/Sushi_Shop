package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * status
 *
 * @author songpeijiang
 * @since 2020/6/10
 */
@Data
@Entity
@Table(name = "STATUS")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "NAME", length = 30, nullable = false)
    private String name;

//    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "status")
//    private List<SushiOrder> sushiOrder;

}
