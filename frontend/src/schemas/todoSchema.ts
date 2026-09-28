import { z } from "zod";

export const todoSchema = z.object({
  task: z.string().trim().min(1, "Task description is required"),
  categoryId: z.coerce.number().min(1, "Category is required"),
});

export type TodoFormData = z.infer<typeof todoSchema>;
