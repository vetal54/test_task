package com.vitalii.uapp_test_task.repository;

import com.vitalii.uapp_test_task.entity.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnRepository extends JpaRepository<Column, Long> {

}
