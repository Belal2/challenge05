package banquemisr.challenge05.service.schadulars;

import banquemisr.challenge05.model.entities.task.Task;
import banquemisr.challenge05.service.email.EmailService;
import banquemisr.challenge05.service.task.TaskServices;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskDeadlineNotifier {

    private final TaskServices taskServices;
    private final EmailService emailService;
    private final JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 */1 * * * *")
    public void notifyUpcomingDeadlines() {
        String lockQuery = "INSERT INTO scheduled_tasks_locks_test (task_name, locked_by, lock_time) VALUES ('notifyUpcomingDeadlines', ?, ?) " +
                "ON CONFLICT (task_name) DO NOTHING";
        String nodeId = "node-" + System.currentTimeMillis();
        int rowsInserted = jdbcTemplate.update(lockQuery, nodeId, LocalDateTime.now());
        if (rowsInserted > 0) {
            try {
                List<Task> tasks = taskServices.getTasksWithUpcomingDeadlines();
                for (Task task : tasks) {
                    String email = task.getCreatedBy();
                    String subject = "Upcoming Deadline for Task: " + task.getTitle();
                    String body = "Your task \"" + task.getTitle() + "\" is due on " + task.getDueDate() + ". Please take action.";
                    System.err.println("send mail ... " + email + " body : " + body);
                    //                    emailService.sendSimpleEmail(email, subject, body);
                }
            } finally {
                String releaseQuery = "DELETE FROM scheduled_tasks_locks_test WHERE task_name = 'notifyUpcomingDeadlines' AND locked_by = ?";
                jdbcTemplate.update(releaseQuery, nodeId);
            }
        } else {
            System.out.println("Another instance is already running the scheduled task.");
        }
    }
}
