import type { Category } from "../types/category";

export async function getCategories(): Promise<Category[]> {
  const response = await fetch("/data/categories.json");

  if (!response.ok) {
    throw new Error("Failed to fetch categories");
  }
  const data = await response.json();

  return data.categories;
}
