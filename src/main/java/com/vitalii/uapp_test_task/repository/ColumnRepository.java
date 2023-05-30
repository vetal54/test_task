package com.vitalii.uapp_test_task.repository;

import com.vitalii.uapp_test_task.entity.Column;
import com.vitalii.uapp_test_task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnRepository extends JpaRepository<Column, Long> {

}
