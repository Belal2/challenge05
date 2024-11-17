package banquemisr.challenge05.repository.task;

import banquemisr.challenge05.model.entities.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAll(Specification<Task> specification, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN :now AND :upcoming AND t.status = 'IN_PROGRESS'")
    List<Task> findTasksWithUpcomingDeadlines(LocalDateTime now, LocalDateTime upcoming);
}
