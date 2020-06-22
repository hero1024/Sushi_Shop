package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * sushi_order
 *
 * @author songpeijiang
 * @since 2020/6/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SUSHI_ORDET")
public class SushiOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "STATUS_ID", nullable = false)
    private int statusId;
    @Column(name = "SUSHI_ID", nullable = false)
    private int sushiId;
    @Column(name = "CREATEDAT", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false, nullable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp createdAt;

//    @ManyToOne
//    @JoinColumn(name = "STATUS_ID")
//    private Status status;
//
//    @ManyToOne
//    @JoinColumn(name = "SUSHI_ID")
//    private Sushi sushi;

}
