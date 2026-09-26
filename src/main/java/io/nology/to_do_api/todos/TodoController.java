package io.nology.to_do_api.todos;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.nology.to_do_api.todos.dtos.CreateTodoDTO;
import io.nology.to_do_api.todos.dtos.UpdateTodoDTO;
import io.nology.to_do_api.todos.entities.Todo;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoservice;

    public TodoController(TodoService service) {
        this.todoservice = service;
    }

    @GetMapping()
    public ResponseEntity<List<Todo>> findAllTodo(
            @RequestParam(required = false) Long categoryId) {

        if (categoryId != null) {
            List<Todo> todos = this.todoservice.getAllByCategory(categoryId);
            return ResponseEntity.ok(todos);
        }

        List<Todo> todos = this.todoservice.getAll();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable Long id) {
        Todo todo = this.todoservice.getByID(id);
        return ResponseEntity.ok(todo);
    }

    @PostMapping()
    public ResponseEntity<Todo> addTodo(
            @RequestBody @Valid CreateTodoDTO data) {

        Todo todo = this.todoservice.createTodo(data);
        return ResponseEntity.status(201).body(todo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTodoDTO data) {

        Todo todo = this.todoservice.updateTodo(data, id);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable Long id) {
        this.todoservice.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}