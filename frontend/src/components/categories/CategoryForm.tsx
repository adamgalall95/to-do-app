import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import classes from "./CategoryForm.module.scss";

const categorySchema = z.object({
  name: z.string().min(1, "Category name is required"),
});

type CategoryFormData = z.infer<typeof categorySchema>;

export function CategoryForm() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<CategoryFormData>({ resolver: zodResolver(categorySchema) });

  const onSubmit = (data: CategoryFormData) => {
    console.log(data);
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
