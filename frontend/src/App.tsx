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
        <h1 className={classes.layout__title}>My Tasks</h1>

        <section className={classes.layout__section}>
          <h2 className={classes.layout__heading}>Task Categories</h2>
          <CategoryForm />
        </section>

        <section className={classes.layout__section}>
          <h2 className={classes.layout__heading}>Task Form</h2>
          <Todoform />
        </section>

        <section className={classes.layout__section}>
          <h2 className={classes.layout__heading}>Tasks</h2>
          <TodoList />
        </section>
      </main>
    </QueryClientProvider>
  );
}
