import { useCategories } from "../../hooks/useCategories";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { createTodo } from "../../services/task-services";
import { useQueryClient } from "@tanstack/react-query";

const todoSchema = z.object({
  task: z.string().min(1, "Task description is required"),
  categoryId: z.coerce.number().min(1, "Category is required"),
});

type TodoFormData = z.infer<typeof todoSchema>;

export function Todoform() {
  const queryClient = useQueryClient();
  const { data: categories } = useCategories();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<z.input<typeof todoSchema>, unknown, z.output<typeof todoSchema>>(
    { resolver: zodResolver(todoSchema) },
  );

  const onSubmit = async (data: TodoFormData) => {
    try {
      const response = await createTodo(data.task, data.categoryId);
      console.log(response);
      queryClient.invalidateQueries({
        queryKey: ["todos"],
      });
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <input {...register("task")} />
      <select {...register("categoryId")} defaultValue="">
        <option value="" disabled>
          Select category
        </option>
        {categories?.map((category) => (
          <option key={category.categoryId} value={category.categoryId}>
            {category.categoryName}
          </option>
        ))}
      </select>
      <button type="submit">Add</button>
      {errors.task && <p>{errors.task.message}</p>}

      {errors.categoryId && <p>{errors.categoryId.message}</p>}
    </form>
  );
}
