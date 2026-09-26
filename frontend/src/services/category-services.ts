import type { Category } from "../types/category";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL;

export const getCategories = async () => {
  const response = await fetch(BACKEND_URL + "/categories");

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message);
  }

  return (await response.json()) as Category[];
};

export const createCategory = async (categoryName: string) => {
  const response = await fetch(BACKEND_URL + "/categories", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ categoryName }),
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message);
  }

  return (await response.json()) as Category;
};
