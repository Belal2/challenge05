package banquemisr.challenge05.model.dto.task;

import banquemisr.challenge05.model.dto.BaseDto;
import banquemisr.challenge05.model.enums.Priority;
import banquemisr.challenge05.model.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TaskDTO extends BaseDto {
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDateTime dueDate;
}
