package banquemisr.challenge05.service.task;

import banquemisr.challenge05.model.dto.task.TaskDTO;
import banquemisr.challenge05.model.entities.task.Task;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskServices {
    TaskDTO createTask(TaskDTO taskDTO);

    Page<TaskDTO> getAllTasks(int page, int size, String title, String status, String priority);

    TaskDTO getTaskById(Long id);

    TaskDTO updateTask(Long id, TaskDTO taskDTO);

    void deleteTask(Long id);

    List<Task> getTasksWithUpcomingDeadlines();
}
