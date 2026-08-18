import classes from "./App.module.scss";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { TodoList } from "./components/todos/TodoList";
import { CategoryForm } from "./components/categories/CategoryForm";
import { Todoform } from "./components/todos/TodoForm";

const queryClient = new QueryClient();

export function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <main className={classes.layout}>
        <h1>Task Categories</h1>
        <CategoryForm />
        <h1>Todos</h1>
        <Todoform />
        <TodoList />
      </main>
    </QueryClientProvider>
  );
}
