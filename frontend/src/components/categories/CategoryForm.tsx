import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import classes from "./CategoryForm.module.scss";
import { useCreateCategory } from "../../hooks/useCreateCategory";
import { useCategories } from "../../hooks/useCategories";
import {
  categorySchema,
  type CategoryFormData,
} from "../../schemas/categorySchema";

export function CategoryForm() {
  const { data: categories = [] } = useCategories();
  const { mutate, isError, error } = useCreateCategory();

  const {
    register,
    handleSubmit,
    reset,
    setError,
    formState: { errors },
  } = useForm<CategoryFormData>({
    resolver: zodResolver(categorySchema),
  });

  const onSubmit = (data: CategoryFormData) => {
    const categoryExists = categories.some(
      (category) => category.categoryName === data.name,
    );

    if (categoryExists) {
      setError("name", {
        message: "Category already exists",
      });
      return;
    }

    mutate(data.name, {
      onSuccess: () => {
        reset();
      },
    });
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

      {isError && (
        <p className={classes.categoryForm__error}>{error.message}</p>
      )}
    </form>
  );
}
