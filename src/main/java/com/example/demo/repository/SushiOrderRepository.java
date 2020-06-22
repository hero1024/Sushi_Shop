package com.example.demo.repository;

import com.example.demo.entity.SushiOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author songpeijiang
 * @since 2020/6/10
 */
public interface SushiOrderRepository extends JpaRepository<SushiOrder, Integer>, JpaSpecificationExecutor<SushiOrder> {
    List<SushiOrder> findByStatusIdOrderByCreatedAtAsc(int status);
}
