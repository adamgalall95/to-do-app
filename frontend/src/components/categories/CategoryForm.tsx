import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import classes from "./CategoryForm.module.scss";
import { createCategory } from "../../services/category-services";
import { useQueryClient } from "@tanstack/react-query";
import { useCategories } from "../../hooks/useCategories";
import { formatText } from "../../utils/formatTexts";

const categorySchema = z.object({
  name: z.string().min(1, "Category name is required"),
});

type CategoryFormData = z.infer<typeof categorySchema>;

export function CategoryForm() {
  const queryClient = useQueryClient();

  const {
    register,
    handleSubmit,
    reset,
    setError,
    formState: { errors },
  } = useForm<CategoryFormData>({
    resolver: zodResolver(categorySchema),
  });

  const { data: categories } = useCategories();

  const onSubmit = async (data: CategoryFormData) => {
    const cleanName = formatText(data.name);

    const categoryExists = categories?.some(
      (category) => category.categoryName === cleanName,
    );

    if (categoryExists) {
      setError("name", {
        message: "Category already exists",
      });
      return;
    }

    try {
      await createCategory(cleanName);

      reset();

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
