import { describe, it, expect } from "vitest";
import { categorySchema } from "./categorySchema";

describe("categorySchema", () => {
  it("accepts a valid category", () => {
    const result = categorySchema.safeParse({
      name: "Work",
    });

    expect(result.success).toBe(true);
  });

  it("rejects an empty category name", () => {
    const result = categorySchema.safeParse({
      name: "",
    });

    expect(result.success).toBe(false);
  });

  it("rejects whitespace-only category names", () => {
    const result = categorySchema.safeParse({
      name: "   ",
    });

    expect(result.success).toBe(false);
  });
});
