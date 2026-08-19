import type { Todo } from "../../types/todo";
import { TodoCard } from "./TodoCard";
import classes from "./TodoList.module.scss";
import { useTodos } from "../../hooks/useTodos";
import { useCategories } from "../../hooks/useCategories";
import {
  deleteTodo,
  updateTodo,
  createTodo,
} from "../../services/task-services";
import { useQueryClient } from "@tanstack/react-query";

export function TodoList() {
  const { data: todos } = useTodos();
  const { data: categories } = useCategories();
  const queryClient = useQueryClient();

  const handleDelete = async (id: number) => {
    await deleteTodo(id);
    queryClient.invalidateQueries({
      queryKey: ["todos"],
    });
  };

  const handleDuplicate = async (todo: Todo) => {
    await createTodo(todo.task, todo.categoryId);

    queryClient.invalidateQueries({
      queryKey: ["todos"],
    });
  };

  const handleTaskChange = async (id: number, task: string) => {
    await updateTodo(id, { task });
    queryClient.invalidateQueries({
      queryKey: ["todos"],
    });
  };

  const handleCategoryChange = async (id: number, categoryId: number) => {
    await updateTodo(id, { categoryId });
    queryClient.invalidateQueries({
      queryKey: ["todos"],
    });
  };

  const handleToggleComplete = async (id: number) => {
    const todo = todos?.find((todo) => todo.id === id);

    if (!todo) return;

    await updateTodo(id, {
      completed: !todo.completed,
    });
    queryClient.invalidateQueries({
      queryKey: ["todos"],
    });
  };

  return (
    <section className={classes.todoList}>
      {todos?.map((todo) => (
        <TodoCard
          key={todo.id}
          todo={todo}
          categories={categories ?? []}
          onDelete={handleDelete}
          onDuplicate={handleDuplicate}
          onTaskChange={handleTaskChange}
          onCategoryChange={handleCategoryChange}
          onToggleComplete={handleToggleComplete}
        />
      ))}
    </section>
  );
}
