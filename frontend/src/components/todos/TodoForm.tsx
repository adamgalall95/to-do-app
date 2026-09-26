import { useCategories } from "../../hooks/useCategories";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import classes from "./TodoForm.module.scss";
import { formatText } from "../../utils/formatTexts";
import { useCreateTodo } from "../../hooks/useCreateTodo";

const todoSchema = z.object({
  task: z.string().trim().min(1, "Task description is required"),
  categoryId: z.coerce.number().min(1, "Category is required"),
});

type TodoFormData = z.infer<typeof todoSchema>;

export function Todoform() {
  const { data: categories } = useCategories();
  const { mutate, isError, error } = useCreateTodo();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<z.input<typeof todoSchema>, unknown, z.output<typeof todoSchema>>(
    { resolver: zodResolver(todoSchema) },
  );

  const onSubmit = (data: TodoFormData) => {
    const cleanTask = formatText(data.task);
    mutate({
      task: cleanTask,
      categoryId: data.categoryId,
    });
  };

  return (
    <form className={classes.todoForm} onSubmit={handleSubmit(onSubmit)}>
      <input
        className={classes.todoForm__input}
        type="text"
        placeholder="What needs doing?"
        {...register("task")}
      />

      <select
        className={classes.todoForm__select}
        {...register("categoryId")}
        defaultValue=""
      >
        <option value="" disabled>
          Select category
        </option>

        {categories?.map((category) => (
          <option key={category.categoryId} value={category.categoryId}>
            {category.categoryName}
          </option>
        ))}
      </select>

      <button className={classes.todoForm__button} type="submit">
        Add
      </button>

      {errors.task && (
        <p className={classes.todoForm__error}>{errors.task.message}</p>
      )}

      {errors.categoryId && (
        <p className={classes.todoForm__error}>{errors.categoryId.message}</p>
      )}

      {isError && <p className={classes.todoForm__error}>{error.message}</p>}
    </form>
  );
}
