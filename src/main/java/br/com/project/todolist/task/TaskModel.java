package br.com.project.todolist.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import java.util.UUID;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String description;
    
    @Column(length = 50) // tamanho máximo de caracters
    private String title;
    
    private String priority;
    
    private UUID idUser;
    private LocalDateTime startAt;
    
    private LocalDateTime endAt;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}