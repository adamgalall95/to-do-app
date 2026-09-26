package io.nology.to_do_api.todos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import io.nology.to_do_api.categories.CategoryRepository;
import io.nology.to_do_api.categories.entities.Category;
import io.nology.to_do_api.common.exceptions.CategoryNotFoundException;
import io.nology.to_do_api.common.exceptions.TodoNotFoundException;
import io.nology.to_do_api.todos.TodoRepository;
import io.nology.to_do_api.todos.TodoService;
import io.nology.to_do_api.todos.dtos.CreateTodoDTO;
import io.nology.to_do_api.todos.dtos.UpdateTodoDTO;
import io.nology.to_do_api.todos.entities.Todo;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

        @Mock
        private TodoRepository repo;

        @Mock
        private CategoryRepository categoryRepo;

        @Mock
        private ModelMapper mapper;

        @InjectMocks
        private TodoService service;

        // 1. getAll

        @Test
        void getAll_CallsFindAll() {

                Todo todo1 = new Todo();
                Todo todo2 = new Todo();

                when(repo.findAll()).thenReturn(List.of(todo1, todo2));

                List<Todo> result = service.getAll();

                assertEquals(2, result.size());
                verify(repo).findAll();
        }

        // 2. getAllByCategory

        @Test
        void getAllByCategory_CallsFindByCategoryId() {

                long categoryId = 1L;

                Todo todo1 = new Todo();
                Todo todo2 = new Todo();

                when(repo.findByCategoryId(categoryId))
                                .thenReturn(List.of(todo1, todo2));

                List<Todo> result = service.getAllByCategory(categoryId);

                assertEquals(2, result.size());
                verify(repo).findByCategoryId(categoryId);
        }

        // 3. getByID

        @Test
        void getByID_CallsFindById() {

                long id = 1L;

                Todo todo = new Todo();
                todo.setId(id);
                todo.setTask("Buy groceries");

                when(repo.findById(id))
                                .thenReturn(Optional.of(todo));

                Todo result = service.getByID(id);

                assertEquals(todo, result);
                verify(repo).findById(id);
        }

        // 4. getByID missing

        @Test
        void getByID_WhenTodoDoesNotExist_ThrowsTodoNotFoundException() {

                long id = 99L;

                when(repo.findById(id))
                                .thenReturn(Optional.empty());

                assertThrows(
                                TodoNotFoundException.class,
                                () -> service.getByID(id));

                verify(repo).findById(id);
        }

        // 5. create valid

        @Test
        void createTodo_WhenCategoryExists_SavesTodo() {

                CreateTodoDTO dto = new CreateTodoDTO();
                dto.setTask("Buy groceries");
                dto.setCategoryId(1L);

                Category category = new Category();

                Todo todo = new Todo();
                todo.setTask("Buy groceries");
                todo.setCategoryId(1L);

                when(categoryRepo.findById(1L))
                                .thenReturn(Optional.of(category));

                when(mapper.map(dto, Todo.class))
                                .thenReturn(todo);

                when(repo.saveAndFlush(todo))
                                .thenReturn(todo);

                Todo result = service.createTodo(dto);

                assertEquals(todo, result);

                verify(categoryRepo).findById(1L);
                verify(mapper).map(dto, Todo.class);
                verify(repo).saveAndFlush(todo);
        }

        // 6. create invalid category

        @Test
        void createTodo_WhenCategoryDoesNotExist_ThrowsCategoryNotFoundException() {

                CreateTodoDTO dto = new CreateTodoDTO();
                dto.setTask("Buy groceries");
                dto.setCategoryId(1L);

                when(categoryRepo.findById(1L))
                                .thenReturn(Optional.empty());

                assertThrows(
                                CategoryNotFoundException.class,
                                () -> service.createTodo(dto));

                verify(categoryRepo).findById(1L);
                verify(mapper, never()).map(any(), eq(Todo.class));
                verify(repo, never()).saveAndFlush(any(Todo.class));
        }

        // 7. update existing

        @Test
        void updateTodo_WhenTodoExists_UpdatesAndSavesTodo() {

                long id = 1L;

                Todo todo = new Todo();
                todo.setId(id);
                todo.setTask("Old task");
                todo.setCategoryId(1L);
                todo.setCompleted(false);

                UpdateTodoDTO dto = new UpdateTodoDTO();
                dto.setTask("New task");
                dto.setCategoryId(2L);
                dto.setCompleted(true);

                Category category = new Category();

                when(repo.findById(id))
                                .thenReturn(Optional.of(todo));

                when(categoryRepo.findById(2L))
                                .thenReturn(Optional.of(category));

                when(repo.save(todo))
                                .thenReturn(todo);

                Todo result = service.updateTodo(dto, id);

                assertEquals("New task", result.getTask());
                assertEquals(2L, result.getCategoryId());
                assertTrue(result.isCompleted());

                verify(repo).findById(id);
                verify(categoryRepo).findById(2L);
                verify(repo).save(todo);
        }

        // 8. update missing

        @Test
        void updateTodo_WhenTodoDoesNotExist_ThrowsTodoNotFoundException() {

                long id = 99L;

                UpdateTodoDTO dto = new UpdateTodoDTO();
                dto.setTask("New task");
                dto.setCompleted(true);

                when(repo.findById(id))
                                .thenReturn(Optional.empty());

                assertThrows(
                                TodoNotFoundException.class,
                                () -> service.updateTodo(dto, id));

                verify(repo).findById(id);
                verify(repo, never()).save(any(Todo.class));
        }

        // 9. delete existing

        @Test
        void deleteById_WhenTodoExists_DeletesTodo() {

                long id = 1L;

                Todo todo = new Todo();
                todo.setId(id);

                when(repo.findById(id))
                                .thenReturn(Optional.of(todo));

                Todo result = service.deleteById(id);

                assertEquals(todo, result);

                verify(repo).findById(id);
                verify(repo).deleteById(id);
        }

        // 10. delete missing

        @Test
        void deleteById_WhenTodoDoesNotExist_ThrowsTodoNotFoundException() {

                long id = 99L;

                when(repo.findById(id))
                                .thenReturn(Optional.empty());

                assertThrows(
                                TodoNotFoundException.class,
                                () -> service.deleteById(id));

                verify(repo).findById(id);
                verify(repo, never()).deleteById(id);
        }
}