import { useMutation, useQueryClient } from "@tanstack/react-query";
import { deleteTodo } from "../services/task-services";

export function useDeleteTodo() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: deleteTodo,

    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["todos"],
      });
    },
  });
}
