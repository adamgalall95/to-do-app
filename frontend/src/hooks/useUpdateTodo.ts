import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateTodo } from "../services/task-services";

export function useUpdateTodo() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      id,
      data,
    }: {
      id: number;
      data: {
        task?: string;
        categoryId?: number;
        completed?: boolean;
      };
    }) => updateTodo(id, data),

    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["todos"],
      });
    },
  });
}
