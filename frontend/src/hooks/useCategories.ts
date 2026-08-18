import { useQuery } from "@tanstack/react-query";
import { getCategories } from "../services/category-services";

export function useCategories() {
  return useQuery({
    queryKey: ["categories"],
    queryFn: getCategories,
  });
}
