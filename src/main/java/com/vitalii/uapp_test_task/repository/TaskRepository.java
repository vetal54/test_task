package com.vitalii.uapp_test_task.repository;

import com.vitalii.uapp_test_task.entity.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  List<Task> findAllByColumnId(Long id);
}
