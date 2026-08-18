import type { Todo } from "../../types/todo";
import { TodoCard } from "./TodoCard";
import classes from "./TodoList.module.scss";
import { useTodos } from "../../hooks/useTodos";
import { useCategories } from "../../hooks/useCategories";

export function TodoList() {
  const { data: todos } = useTodos();
  const { data: categories } = useCategories();

  const handleDelete = (id: number) => {
    console.log("delete", id);
  };

  const handleDuplicate = (todo: Todo) => {
    console.log("duplicate", todo);
  };

  const handleTaskChange = (id: number, task: string) => {
    console.log("task changed", id, task);
  };

  const handleCategoryChange = (id: number, categoryId: number) => {
    console.log("category changed", id, categoryId);
  };

  const handleToggleComplete = (id: number) => {
    console.log("toggle complete", id);
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
