import type { ChangeEvent } from "react";
import type { Todo } from "../../types/todo";
import classes from "./TodoCard.module.scss";
import type { Category } from "../../types/category";

type TodoCardProps = {
  todo: Todo;
  categories: Category[];
  onDelete: (id: number) => void;
  onDuplicate: (todo: Todo) => void;
  onTaskChange: (id: number, task: string) => void;
  onCategoryChange: (id: number, categoryId: number) => void;
  onToggleComplete: (id: number) => void;
};

export function TodoCard({
  todo,
  categories,
  onDelete,
  onDuplicate,
  onToggleComplete,
  onTaskChange,
  onCategoryChange,
}: TodoCardProps) {
  const handleTaskChange = (event: ChangeEvent<HTMLInputElement>) => {
    onTaskChange(todo.id, event.target.value);
  };
  const handleCategoryChange = (event: ChangeEvent<HTMLSelectElement>) => {
    onCategoryChange(todo.id, Number(event.target.value));
  };
  return (
    <article className={classes.todoCard}>
      <div className={classes.todoCardContent}>
        <input
          className={classes.todoCardTask}
          type="text"
          defaultValue={todo.task}
          onChange={handleTaskChange}
        />

        <select
          className={classes.todoCardCategory}
          value={todo.categoryId}
          onChange={handleCategoryChange}
        >
          {categories.map((category) => (
            <option key={category.categoryId} value={category.categoryId}>
              {category.categoryName}
            </option>
          ))}
        </select>
      </div>

      <div className={classes.todoCardActions}>
        <button
          className={`${classes.todoCardButton} ${
            todo.completed ? classes.todoCardButtonCompleted : ""
          }`}
          type="button"
          onClick={() => onToggleComplete(todo.id)}
          aria-label={
            todo.completed ? "Mark as incomplete" : "Mark as complete"
          }
        >
          ✓
        </button>

        <button
          className={classes.todoCardButton}
          type="button"
          onClick={() => onDuplicate(todo)}
          aria-label="Duplicate"
        >
          ⧉
        </button>

        <button
          className={`${classes.todoCardButton} ${classes.todoCardDelete}`}
          type="button"
          onClick={() => onDelete(todo.id)}
          aria-label="Delete"
        >
          ×
        </button>
      </div>
    </article>
  );
}
