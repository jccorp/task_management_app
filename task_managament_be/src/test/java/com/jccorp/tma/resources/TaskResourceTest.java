package com.jccorp.tma.resources;

import com.jccorp.tma.model.dao.TaskDAO;
import com.jccorp.tma.model.dao.TaskDAOImpl;
import com.jccorp.tma.model.entity.Task;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TaskResourceTest {

    private static final TaskDAO DAO = mock(TaskDAOImpl.class);
    private static final ResourceExtension RESOURCE = ResourceExtension.builder()
            .addResource(new TaskResource(DAO))
            .build();
    private Task task,task3;
    private static final String PATH = "/tasks";

    @BeforeEach
    void setup() {
        task = new Task();
        task.setId(1L);

        task3 = new Task();
        task3.setId(3L);
        task3.setCompleted(false);
    }

    @AfterEach
    void tearDown() {
        reset(DAO);
    }

    @Test
    void testGetTaskSuccess() {
        when(DAO.findById(1L)).thenReturn(Optional.of(task));

        Task found = RESOURCE.target(PATH+"/1").request().get(Task.class);

        assertThat(found.getId()).isEqualTo(task.getId());
        verify(DAO).findById(1L);
    }

    @Test
    void testGetTaskNotFound() {
        when(DAO.findById(2L)).thenReturn(Optional.empty());
        final Response response = RESOURCE.target(PATH+"/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).findById(2L);
    }

    @Test
    void testDeleteTaskSuccess(){
        when(DAO.findById(3L)).thenReturn(Optional.of(task3));
        final Response response = RESOURCE.target(PATH+"/3")
                .request()
                .delete();

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(DAO).findById(3L);
    }

    @Test
    void testDeleteTaskNotFound(){
        when(DAO.findById(4L)).thenReturn(Optional.empty());
        final Response response = RESOURCE.target(PATH+"/4")
                .request()
                .delete();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).findById(4L);
    }

    @Test
    void testCreateTaskSuccess(){
        when(DAO.create(any(Task.class))).thenReturn(task);
        final Response response = RESOURCE.target(PATH)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(task, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.CREATED);
        assertThat(response.getLocation().getPath()).isEqualTo("/tasks/1");
        verify(DAO).create(task);
    }

    @Test
    void testGetAllTaskSuccess(){
        List<Task> taskList = Arrays.asList(task,task3);
        when(DAO.findAll()).thenReturn(taskList);

        List<Task> list = RESOURCE.target(PATH)
                .request()
                .get(new GenericType<List<Task>>() {});

        assertThat(list.size()).isEqualTo(2);
        verify(DAO).findAll();
        assertThat(list).containsAll(taskList);
    }

    @Test
    void testUpdateTaskSuccess(){
        task3.setCompleted(true);
        final Response response = RESOURCE.target(PATH+"/3")
                .request()
                .put(Entity.entity(task3, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        assertThat(response.readEntity(Task.class).getCompleted()).isEqualTo(true);
    }
}
