package com.jccorp.tma.resources;

import com.jccorp.tma.model.dao.TaskDAO;
import com.jccorp.tma.model.entity.Task;
import io.dropwizard.hibernate.UnitOfWork;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON})
@Api(value = "TaskResource")
public class TaskResource {

    private final TaskDAO taskDAO;

    public TaskResource(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @POST
    @UnitOfWork
    @ApiOperation(value = "Create a new task",response = Task.class)
    @ApiResponse(code = 201, message = "Created")
    public Response createTask(@Valid Task task) {
        Task t = taskDAO.create(task);
        URI uri = UriBuilder.fromResource(TaskResource.class).path("/{taskId}").build(t.getId());
        return Response.created(uri).entity(t).build();
    }

    @GET
    @UnitOfWork
    @ApiOperation(value = "Get all the tasks",response = Task[].class)
    @ApiResponse(code = 200, message = "Suceess|OK")
    public List<Task> getAllTasks(){
        return taskDAO.findAll();
    }

    @GET
    @Path("/{taskId}")
    @UnitOfWork
    @ApiOperation(value = "Get specific task")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK", response = Task.class),
            @ApiResponse(code = 404, message = "Not found!") })
    public Response getTask(@PathParam("taskId") long id){
        Optional<Task> task = taskDAO.findById(id);
        return task.map(t -> Response.ok(t).build()).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("/{taskId}")
    @UnitOfWork
    @ApiOperation(value = "Update a task",response = Task.class)
    @ApiResponse(code = 200, message = "Suceess|OK")
    public Task updateTask(@PathParam("taskId") long id,@Valid Task task){
        task.setId(id);
        taskDAO.update(task);
        return task;
    }

    @DELETE
    @Path("/{taskId}")
    @UnitOfWork
    @ApiOperation(value = "Delete the task")
    @ApiResponse(code = 200, message = "Suceess|OK")
    public void delete(@PathParam("taskId") long id){
        taskDAO.delete(taskDAO.findById(id).get());
    }
}
