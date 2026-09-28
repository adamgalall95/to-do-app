import { describe, it, expect } from "vitest";
import { todoSchema } from "./todoSchema";

describe("todoSchema", () => {
  it("accepts valid todo data", () => {
    const result = todoSchema.safeParse({
      task: "Buy groceries",
      categoryId: 1,
    });

    expect(result.success).toBe(true);
  });

  it("rejects an empty task", () => {
    const result = todoSchema.safeParse({
      task: "",
      categoryId: 1,
    });

    expect(result.success).toBe(false);
  });

  it("rejects a missing category", () => {
    const result = todoSchema.safeParse({
      task: "Buy groceries",
      categoryId: 0,
    });

    expect(result.success).toBe(false);
  });
});
