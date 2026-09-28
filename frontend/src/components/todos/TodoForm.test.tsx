import { render, screen } from "@testing-library/react";
import { describe, it, expect } from "vitest";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

import { Todoform } from "./TodoForm";

describe("Todoform", () => {
  it("renders all form fields", () => {
    const queryClient = new QueryClient();

    render(
      <QueryClientProvider client={queryClient}>
        <Todoform />
      </QueryClientProvider>,
    );

    expect(
      screen.getByPlaceholderText("What needs doing?"),
    ).toBeInTheDocument();

    expect(screen.getByRole("combobox")).toBeInTheDocument();

    expect(screen.getByRole("button", { name: "Add" })).toBeInTheDocument();
  });
});
