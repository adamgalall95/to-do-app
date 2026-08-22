package io.nology.to_do_api.categories;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.to_do_api.categories.dtos.CreateCategoryDTO;
import io.nology.to_do_api.categories.entities.Category;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ResponseEntity<List<Category>> findAllCategory() {
        List<Category> categories = this.categoryService.getAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping()
    public ResponseEntity<Category> addCategory(@RequestBody @Valid CreateCategoryDTO data) {
        Category category = this.categoryService.createCategory(data);
        return ResponseEntity.status(201).body(category);
    }

}
