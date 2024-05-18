package com.khaledmosharraf.twtms.service;

import com.khaledmosharraf.twtms.model.Todo;
import com.khaledmosharraf.twtms.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
@SessionAttributes("username")
public class TodoService {
    TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findByUsername(String username) {
        return todoRepository.findByUsername(username);
    }
    public void save(Todo todo) {
        todoRepository.save(todo);
    }
    public void deleteById(int id) {
        todoRepository.deleteById(id);
    }
    public Todo findById(int id) {
        return todoRepository.findById(id).get();
    }


}
