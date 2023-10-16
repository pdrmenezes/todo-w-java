package br.com.pdrmenezes.todo.task;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
  List<TaskModel> findTaskByUserId(UUID userId);
}
