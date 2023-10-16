package br.com.pdrmenezes.todo.task;

import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    taskModel.setId((UUID) userId);

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

}
