import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createTodo } from "../services/task-services";

export function useCreateTodo() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ task, categoryId }: { task: string; categoryId: number }) =>
      createTodo(task, categoryId),

    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["todos"],
      });
    },
  });
}
