package br.com.project.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRespository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        System.out.println("Controller" + request.getAttribute("idUser"));
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);
        
        var currentDate = LocalDateTime.now();

        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.OK).body("Initial date / End date must be bigger than last one");
        }
         if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.OK).body("Initial date date must be smaller than last one");
        }
        var task = this.taskRespository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRespository.findByIdUser((UUID) idUser);
        return tasks;
    }

    @PutMapping("/{id}")
    public TaskModel update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){
        taskModel.setId(id);
        return this.taskRespository.save(taskModel);
    }
}
