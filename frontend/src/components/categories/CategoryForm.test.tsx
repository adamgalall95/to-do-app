import { render, screen } from "@testing-library/react";
import { describe, it, expect } from "vitest";
import { CategoryForm } from "./CategoryForm";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

describe("CategoryForm", () => {
  it("renders all form fields", () => {
    const queryClient = new QueryClient();

    render(
      <QueryClientProvider client={queryClient}>
        <CategoryForm />
      </QueryClientProvider>,
    );

    expect(screen.getByPlaceholderText("Category name")).toBeInTheDocument();

    expect(screen.getByRole("button", { name: "Add" })).toBeInTheDocument();
  });
});
