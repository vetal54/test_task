package com.vitalii.uapp_test_task.repository;

import com.vitalii.uapp_test_task.entity.Domain;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractRepository<E extends Domain> extends JpaRepository<E, UUID> {

}
