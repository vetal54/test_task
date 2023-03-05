package com.vitalii.uapp_test_task.repository;

import com.vitalii.uapp_test_task.entity.Task;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends AbstractRepository<Task> {

  List<Task> findAllByColumnId(UUID id);
}
