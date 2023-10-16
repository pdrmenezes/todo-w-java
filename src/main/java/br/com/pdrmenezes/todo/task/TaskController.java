package br.com.pdrmenezes.todo.task;

import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pdrmenezes.todo.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/create")
  public ResponseEntity createTask(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    var userId = request.getAttribute("userId");
    // "casting" the userId as uuid
    taskModel.setUserId((UUID) userId);

    if (taskModel.getEndAt().isBefore(taskModel.getStartAt())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start Date must be before End Date");
    }

    var createdTask = this.taskRepository.save(taskModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
  }

  @GetMapping("")
  public List<TaskModel> listTasks(HttpServletRequest request) {
    var userId = request.getAttribute("userId");
    var tasks = this.taskRepository.findTaskByUserId((UUID) userId);

    return tasks;
  }

  @PatchMapping("/update/{taskId}")
  public ResponseEntity updateTask(@RequestBody TaskModel taskModel, HttpServletRequest request,
      @PathVariable UUID taskId) {
    var userId = request.getAttribute("userId");
    taskModel.setUserId((UUID) userId);

    var taskToBeEdited = this.taskRepository.findById(taskId).orElse(null);

    // user is not allowed to update a task that doesn't exist
    if (taskToBeEdited == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task not found.");
    }

    // user is not allowed to update a task that it's not theirs
    if (!taskToBeEdited.getUserId().equals(request.getAttribute("userId"))) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Operation not allowed.");
    }
    // copying only the properties from the request to the already existing task
    Utils.copyNonNullProperties(taskModel, taskToBeEdited);

    var updatedTask = this.taskRepository.save(taskToBeEdited);

    return ResponseEntity.status(HttpStatus.CREATED).body(updatedTask);
  }

}
