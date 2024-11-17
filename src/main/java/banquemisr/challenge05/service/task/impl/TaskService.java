package banquemisr.challenge05.service.task.impl;

import banquemisr.challenge05.exceptions.BanqueMisrException;
import banquemisr.challenge05.mappers.TaskMapper;
import banquemisr.challenge05.model.dto.task.TaskDTO;
import banquemisr.challenge05.model.entities.task.Task;
import banquemisr.challenge05.model.enums.Priority;
import banquemisr.challenge05.model.enums.Status;
import banquemisr.challenge05.repository.task.TaskRepository;
import banquemisr.challenge05.service.task.TaskServices;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService implements TaskServices {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    public Page<TaskDTO> getAllTasks(int page, int size, String title, String status, String priority) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Task> specification = buildSpecification(title, status, priority);

        return taskRepository.findAll(specification, pageable).map(taskMapper::toDto);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new BanqueMisrException("task.not.found", HttpStatus.BAD_REQUEST));
        return taskMapper.toDto(task);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new BanqueMisrException("task.not.found", HttpStatus.BAD_REQUEST));
        taskDTO.setId(existingTask.getId());
        taskMapper.updateTask(taskDTO, existingTask);
        existingTask = taskRepository.save(existingTask);
        return taskMapper.toDto(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new BanqueMisrException("task.not.found", HttpStatus.BAD_REQUEST);
        }
        taskRepository.deleteById(id);
    }

    private Specification<Task> buildSpecification(String title, String status, String priority) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (title != null && !title.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }

            if (status != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), Status.valueOf(status)));
            }

            if (priority != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("priority"), Priority.valueOf(priority)));
            }

            return predicate;
        };
    }

    @Override
    public List<Task> getTasksWithUpcomingDeadlines() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime upcoming = now.plusHours(24);
        return taskRepository.findTasksWithUpcomingDeadlines(now, upcoming);
    }

}
