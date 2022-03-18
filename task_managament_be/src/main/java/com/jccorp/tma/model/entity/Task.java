package com.jccorp.tma.model.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tasks")
@NamedQuery(
        name = "com.jccorp.tma.model.entity.Task.findAll",
        query = "SELECT t FROM Task t"
)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Description of the task",name="description",required=true,value="task 1")
    private String description;

    @Column
    private Boolean completed;

    @Column
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    public Task(){

    }

    public Task(String description, Boolean completed, Date dueDate) {
        this.description = description;
        this.completed = completed;
        this.dueDate = dueDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(description, task.description) && Objects.equals(completed, task.completed) && Objects.equals(dueDate, task.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, completed, dueDate);
    }
}
