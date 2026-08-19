import type { Todo } from "../types/todo";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL;

export const getTodos = async () => {
  const response = await fetch(BACKEND_URL + "/todos");

  if (!response.ok) {
    throw new Error("Failed to fetch tasks");
  }

  return (await response.json()) as Todo[];
};

export const createTodo = async (task: string, categoryId: number) => {
  const response = await fetch(BACKEND_URL + "/todos", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      task,
      categoryId,
    }),
  });

  if (!response.ok) {
    throw new Error("Failed to create task");
  }

  return (await response.json()) as Todo;
};

export const deleteTodo = async (id: number) => {
  const response = await fetch(`${BACKEND_URL}/todos/${id}`, {
    method: "DELETE",
  });

  if (!response.ok) {
    throw new Error("Failed to delete task");
  }

  return response;
};

export const updateTodo = async (
  id: number,
  data: {
    task?: string;
    categoryId?: number;
    completed?: boolean;
  },
) => {
  const response = await fetch(`${BACKEND_URL}/todos/${id}`, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    throw new Error("Failed to update task");
  }

  return (await response.json()) as Todo;
};
