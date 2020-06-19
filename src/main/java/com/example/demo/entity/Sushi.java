package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * sushi
 * @author songpeijiang
 * @since 2020/6/10
 * Proxy(lazy = false)避免LazyInitializationException
 */
@Proxy(lazy = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SUSHI")
public class Sushi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "NAME", length = 30, nullable = false)
    private String name;
    @Column(name = "TIME_TO_MAKE")
    private int timeToMake;

//    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "sushi")
//    private List<SushiOrder> sushiOrder;

}
