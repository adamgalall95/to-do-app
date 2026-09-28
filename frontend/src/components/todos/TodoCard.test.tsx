import { render, screen } from "@testing-library/react";
import { describe, it, expect, vi } from "vitest";

import { TodoCard } from "./TodoCard";

const todo = {
  id: 1,
  task: "Buy groceries",
  categoryId: 1,
  completed: false,
};

const categories = [
  {
    categoryId: 1,
    categoryName: "Personal",
  },
  {
    categoryId: 2,
    categoryName: "Work",
  },
];

describe("TodoCard", () => {
  it("renders todo information and actions", () => {
    render(
      <TodoCard
        todo={todo}
        categories={categories}
        onDelete={vi.fn()}
        onDuplicate={vi.fn()}
        onTaskChange={vi.fn()}
        onCategoryChange={vi.fn()}
        onToggleComplete={vi.fn()}
      />,
    );

    expect(screen.getByDisplayValue("Buy groceries")).toBeInTheDocument();
    expect(screen.getByRole("combobox")).toBeInTheDocument();
    expect(
      screen.getByRole("button", { name: "Mark as complete" }),
    ).toBeInTheDocument();
    expect(
      screen.getByRole("button", { name: "Duplicate" }),
    ).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Delete" })).toBeInTheDocument();
  });
});
