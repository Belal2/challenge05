package banquemisr.challenge05.model.entities.task;

import banquemisr.challenge05.model.entities.BaseEntity;
import banquemisr.challenge05.model.enums.Priority;
import banquemisr.challenge05.model.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "task_test")
public class Task extends BaseEntity {
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private LocalDateTime dueDate;


}
