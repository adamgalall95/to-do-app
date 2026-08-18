import type { Todo } from "../types/todo";

export async function getTodos(): Promise<Todo[]> {
  const response = await fetch("/data/todos.json");

  if (!response.ok) {
    throw new Error("Failed to fetch todos");
  }
  const data = await response.json();

  return data.todos;
}
