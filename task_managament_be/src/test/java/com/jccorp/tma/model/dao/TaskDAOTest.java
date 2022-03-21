package com.jccorp.tma.model.dao;

import com.jccorp.tma.model.entity.Task;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TaskDAOTest {

    public DAOTestExtension daoTestRule = DAOTestExtension.newBuilder()
            .addEntityClass(Task.class)
            .build();

    private TaskDAO taskDAO;

    @BeforeEach
    void setUp() throws Exception {
        taskDAO = new TaskDAOImpl(daoTestRule.getSessionFactory());
    }

    @Test
    void shouldCreateTaskAndFindById() {
        Date t = Calendar.getInstance().getTime();
        final Task task1 = daoTestRule.inTransaction(() -> taskDAO.create(new Task("Task 1", false, t)));
        assertThat(task1.getId()).isPositive();
        assertThat(task1.getDescription()).isEqualTo("Task 1");
        assertThat(task1.getCompleted()).isEqualTo(false);
        assertThat(task1.getDueDate()).isEqualTo(t);
        assertThat(taskDAO.findById(task1.getId())).isEqualTo(Optional.of(task1));
    }

    @Test
    void shouldFindAll() {

        Date t1 = Calendar.getInstance().getTime();
        Date t2 = Calendar.getInstance().getTime();
        Date t3 = Calendar.getInstance().getTime();

        daoTestRule.inTransaction(() -> {
            taskDAO.create(new Task("Task 1", false, t1));
            taskDAO.create(new Task("Task 2", true, t2));
            taskDAO.create(new Task("Task 3", false, t3));
        });

        final List<Task> tasks = taskDAO.findAll();
        assertThat(tasks).extracting("description").containsOnly("Task 1", "Task 2", "Task 3");
        assertThat(tasks).extracting("completed").containsOnly(false, true, false);
        assertThat(tasks).extracting("dueDate").containsOnly(t1, t2, t3);
    }

    @Test
    void shouldUpdateTask(){

        final Task task1 = daoTestRule.inTransaction(() -> taskDAO.create(new Task("Task 1", false, Calendar.getInstance().getTime())));
        assertThat(task1.getCompleted()).isEqualTo(false);
        task1.setCompleted(true);
        taskDAO.update(task1);
        Task task1bis = taskDAO.findById(task1.getId()).get();
        assertThat(task1bis.getCompleted()).isEqualTo(true);
    }

    @Test
    void shouldDeleteTask(){
        Date t = Calendar.getInstance().getTime();
        final Task task1 = daoTestRule.inTransaction(() -> taskDAO.create(new Task("Task 1", false, t)));
        taskDAO.delete(task1);
        assertThat(taskDAO.findById(task1.getId())).isEqualTo(Optional.empty());
    }

    @Test
    void shouldHandlesNullFullName() {
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(()->
                daoTestRule.inTransaction(() -> taskDAO.create(new Task(null, false, Calendar.getInstance().getTime()))));
    }
}
