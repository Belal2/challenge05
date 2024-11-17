
CREATE TABLE IF NOT EXISTS legal.scheduled_tasks_locks_test (
    task_name VARCHAR(100) PRIMARY KEY,
    locked_by VARCHAR(100),
    lock_time TIMESTAMP
);

