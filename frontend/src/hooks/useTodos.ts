import { useQuery } from "@tanstack/react-query";
import { getTodos } from "../services/task-services";

export function useTodos() {
  return useQuery({
    queryKey: ["todos"],
    queryFn: getTodos,
  });
}
