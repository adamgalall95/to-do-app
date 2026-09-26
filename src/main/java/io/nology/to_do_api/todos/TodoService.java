package io.nology.to_do_api.todos;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.nology.to_do_api.categories.CategoryRepository;
import io.nology.to_do_api.common.exceptions.CategoryNotFoundException;
import io.nology.to_do_api.common.exceptions.TodoNotFoundException;
import io.nology.to_do_api.todos.dtos.CreateTodoDTO;
import io.nology.to_do_api.todos.dtos.UpdateTodoDTO;
import io.nology.to_do_api.todos.entities.Todo;

@Service
public class TodoService {

    private final TodoRepository repo;
    private final CategoryRepository categoryRepo;
    private final ModelMapper mapper;

    public TodoService(
            TodoRepository serviceRepo,
            CategoryRepository categoryRepo,
            ModelMapper mapper) {

        this.repo = serviceRepo;
        this.categoryRepo = categoryRepo;
        this.mapper = mapper;
    }

    public List<Todo> getAll() {
        return this.repo.findAll();
    }

    public List<Todo> getAllByCategory(long categoryId) {
        return this.repo.findByCategoryId(categoryId);
    }

    public Todo createTodo(CreateTodoDTO data) {

        categoryRepo.findById(data.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(data.getCategoryId()));

        Todo createTodo = this.mapper.map(data, Todo.class);

        return this.repo.saveAndFlush(createTodo);
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

            categoryRepo.findById(data.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(data.getCategoryId()));

            todo.setCategoryId(data.getCategoryId());
        }

        if (data.getCompleted() != null) {
            todo.setCompleted(data.getCompleted());
        }

        return this.repo.save(todo);
    }

    public Todo deleteById(long id) {

        Todo todo = this.getByID(id);

        repo.deleteById(id);

        return todo;
    }
}