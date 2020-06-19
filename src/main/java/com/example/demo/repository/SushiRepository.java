package com.example.demo.repository;

import com.example.demo.entity.Sushi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author songpeijiang
 * @since 2020/6/10
 */
public interface SushiRepository extends JpaRepository<Sushi, Integer>, JpaSpecificationExecutor<Sushi> {
    Sushi findByName(String name);
}
