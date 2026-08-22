package io.nology.to_do_api.todos;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.nology.to_do_api.exceptions.TodoNotFoundException;
import io.nology.to_do_api.todos.dtos.CreateTodoDTO;
import io.nology.to_do_api.todos.dtos.UpdateTodoDTO;
import io.nology.to_do_api.todos.entities.Todo;

@Service
public class TodoService {

    private final TodoRepository repo;
    private final ModelMapper mapper;

    public TodoService(TodoRepository serviceRepo, ModelMapper mapper) {
        this.repo = serviceRepo;
        this.mapper = mapper;
    }

    public List<Todo> getAll() {
        return this.repo.findAll();
    }

    public List<Todo> getAllByCategory(long categoryId) {
        return this.repo.findByCategoryId(categoryId);
    }

    public Todo createTodo(CreateTodoDTO data) {
        Todo createTodo = this.mapper.map(data, Todo.class);
        this.repo.saveAndFlush(createTodo);
        return createTodo;
    }

    public Todo getByID(long id) {
        return this.repo.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    public Todo updateTodo(UpdateTodoDTO data, long id) {
        Todo todo = this.getByID(id);

        if (data.getTask() != null) {
            todo.setTask(data.getTask());
        }

        if (data.getCategoryId() != null) {
            todo.setCategoryId(data.getCategoryId());
        }

        if (data.getCompleted() != null) {
            todo.setCompleted(data.getCompleted());
        }

        return this.repo.save(todo);
    }

    public Todo deletById(long id) {
        Todo todo = getByID(id);

        repo.deleteById(id);

        return todo;
    }
}