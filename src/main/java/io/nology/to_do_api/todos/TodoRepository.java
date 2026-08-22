package io.nology.to_do_api.todos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.nology.to_do_api.todos.entities.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByCategoryId(long categoryId);
}