package com.jccorp.tma.model.dao;

import com.jccorp.tma.model.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskDAO  {

    Optional<Task> findById(Long id);

    Task create(Task task);

    List<Task> findAll();

    void delete(Task task);

    void update(Task task);
}
