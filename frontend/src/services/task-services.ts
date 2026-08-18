import type { Todo } from "../types/todo";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL;

export const getTodos = async () => {
  const response = await fetch(BACKEND_URL + "/todos");

  if (!response.ok) {
    throw new Error("Failed to fetch tasks");
  }

  return (await response.json()) as Todo[];
};
