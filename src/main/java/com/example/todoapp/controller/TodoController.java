package com.example.todoapp.controller;

import com.example.todoapp.model.Todo;
import com.example.todoapp.repository.TodoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Tag(name = "GET", description = "GET methods for Todos")
    @Operation(summary = "Get list of all Todos")
    @GetMapping
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Tag(name="POST", description = "POST methods for Todos")
    @Operation(summary = "Create a Todo")
    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    @Tag(name = "GET", description = "GET methods for Todos")
    @Operation(summary = "Get Todos by their ID")
    @GetMapping("/{id}")
    public Todo getTodoById(@Parameter(
            description = "ID of Todo to retrieve", required = true)
                                @PathVariable Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
    }

    @Tag(name = "PUT")
    @Operation(summary = "Update a Todo")
    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
        todo.setTitle(todoDetails.getTitle());
        todo.setCompleted(todoDetails.isCompleted());
        return todoRepository.save(todo);
    }

    @Tag(name = "DELETE")
    @Operation(summary = "Delete a Todo")
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }
}
