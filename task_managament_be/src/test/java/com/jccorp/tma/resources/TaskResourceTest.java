package com.jccorp.tma.resources;

import com.jccorp.tma.model.dao.TaskDAO;
import com.jccorp.tma.model.entity.Task;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TaskResourceTest {

    private static final TaskDAO DAO = mock(TaskDAO.class);
    private static final ResourceExtension EXT = ResourceExtension.builder()
            .addResource(new TaskResource(DAO))
            .build();
    private Task task;

    @BeforeEach
    void setup() {
        task = new Task();
        task.setId(1L);
    }

    @AfterEach
    void tearDown() {
        reset(DAO);
    }

    @Test
    void getTaskSuccess() {
        when(DAO.findById(1L)).thenReturn(Optional.of(task));

        Task found = EXT.target("/tasks/1").request().get(Task.class);

        assertThat(found.getId()).isEqualTo(task.getId());
        verify(DAO).findById(1L);
    }

    @Test
    void getTaskNotFound() {
        when(DAO.findById(2L)).thenReturn(Optional.empty());
        final Response response = EXT.target("/tasks/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).findById(2L);
    }


}
