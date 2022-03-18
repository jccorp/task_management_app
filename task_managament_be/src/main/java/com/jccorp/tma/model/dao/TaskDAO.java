package com.jccorp.tma.model.dao;

import com.jccorp.tma.model.entity.Task;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class TaskDAO extends AbstractDAO<Task> {
    public TaskDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Task create(Task task) {
        return persist(task);
    }

    public List<Task> findAll() {
        return list(namedTypedQuery("com.jccorp.tma.model.entity.Task.findAll"));
    }

    public void delete(Task task){
        currentSession().delete(task);
    }

    public void update(Task task){
        currentSession().saveOrUpdate(task);
    }
}
