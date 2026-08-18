import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import classes from "./CategoryForm.module.scss";
import { createCategory } from "../../services/category-services";
import { useQueryClient } from "@tanstack/react-query";

const categorySchema = z.object({
  name: z.string().min(1, "Category name is required"),
});

type CategoryFormData = z.infer<typeof categorySchema>;

export function CategoryForm() {
  const queryClient = useQueryClient();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<CategoryFormData>({ resolver: zodResolver(categorySchema) });

  const onSubmit = async (data: CategoryFormData) => {
    try {
      const newCategory = await createCategory(data.name);
      console.log(newCategory);
      queryClient.invalidateQueries({
        queryKey: ["categories"],
      });
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <form className={classes.categoryForm} onSubmit={handleSubmit(onSubmit)}>
      <input
        className={classes.categoryForm__input}
        type="text"
        placeholder="Category name"
        {...register("name")}
      />

      <button className={classes.categoryForm__button} type="submit">
        Add
      </button>

      {errors.name && (
        <p className={classes.categoryForm__error}>{errors.name.message}</p>
      )}
    </form>
  );
}
