import type { Todo } from "../../types/todo";
import { TodoCard } from "./TodoCard";
import classes from "./TodoList.module.scss";
import { useTodos } from "../../hooks/useTodos";
import { useCategories } from "../../hooks/useCategories";
import { useDeleteTodo } from "../../hooks/useDeleteTodo";
import { useCreateTodo } from "../../hooks/useCreateTodo";
import { useUpdateTodo } from "../../hooks/useUpdateTodo";

export function TodoList() {
  const { data: todos = [] } = useTodos();
  const { data: categories = [] } = useCategories();

  const { mutateAsync: deleteTodo } = useDeleteTodo();
  const { mutateAsync: createTodo } = useCreateTodo();
  const { mutateAsync: updateTodo } = useUpdateTodo();

  const handleDelete = async (id: number) => {
    await deleteTodo(id);
  };

  const handleDuplicate = async (todo: Todo) => {
    await createTodo({
      task: todo.task,
      categoryId: todo.categoryId,
    });
  };

  const handleTaskChange = async (id: number, task: string) => {
    await updateTodo({
      id,
      data: { task },
    });
  };

  const handleCategoryChange = async (id: number, categoryId: number) => {
    await updateTodo({
      id,
      data: { categoryId },
    });
  };

  const handleToggleComplete = async (id: number) => {
    const todo = todos.find((todo) => todo.id === id);

    if (!todo) return;

    await updateTodo({
      id,
      data: {
        completed: !todo.completed,
      },
    });
  };

  return (
    <section className={classes.todoList}>
      {todos.map((todo) => (
        <TodoCard
          key={todo.id}
          todo={todo}
          categories={categories}
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
