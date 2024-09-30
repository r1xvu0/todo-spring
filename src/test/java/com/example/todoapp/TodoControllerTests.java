package com.example.todoapp;

import com.example.todoapp.model.Todo;
import com.example.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodoControllerTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TodoRepository todoRepository;

    private Todo todo;

    @BeforeEach
    public void setUp() {
        todoRepository.deleteAll();
        todo = new Todo();
        todo.setTitle("Test Todo");
        todo.setCompleted(false);
    }

    @Test
    public void shouldReturn200WhenGettingAllTodos() {
        todoRepository.save(todo);

        ResponseEntity<List> response = testRestTemplate.getForEntity("/api/todos", List.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isNotNull();
        then(response.getBody().size()).isGreaterThan(0);
    }

    @Test
    public void shouldCreateTodo() {
        ResponseEntity<Todo> response = testRestTemplate.postForEntity("/api/todos", todo, Todo.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isNotNull();
        then(response.getBody().getTitle()).isEqualTo(todo.getTitle());
    }

    @Test
    public void shouldReturn200WhenGettingTodoById() {
        Todo savedTodo = todoRepository.save(todo);

        ResponseEntity<Todo> response = testRestTemplate.getForEntity("/api/todos/" + savedTodo.getId(), Todo.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isNotNull();
        then(response.getBody().getId()).isEqualTo(todo.getId());
    }

    @Test
    public void shouldUpdateTodo() {
        Todo savedTodo = todoRepository.save(todo);
        String newTitle = "Updated through Test";
        savedTodo.setTitle(newTitle);

        testRestTemplate.put("/api/todos/" + savedTodo.getId(), savedTodo);

        ResponseEntity<Todo> response = testRestTemplate.getForEntity("/api/todos/" + savedTodo.getId(), Todo.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody().getTitle()).isEqualTo(newTitle);
    }

    @Test
    public void shouldDeleteTodo() {
        Todo savedTodo = todoRepository.save(todo);

        then(savedTodo).isNotNull();
        then(savedTodo.getId()).isGreaterThan(0);

        testRestTemplate.delete("/api/todos/" + savedTodo.getId());

        ResponseEntity<Todo> response = testRestTemplate.getForEntity("/api/todos/" + savedTodo.getId(), Todo.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
