package com.example.demo.repository;

import com.example.demo.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author songpeijiang
 * @since 2020/6/10
 */
public interface StatusRepository extends JpaRepository<Status, Integer>, JpaSpecificationExecutor<Status> {
}
