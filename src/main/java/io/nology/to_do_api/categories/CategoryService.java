package io.nology.to_do_api.categories;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.nology.to_do_api.categories.dtos.CreateCategoryDTO;
import io.nology.to_do_api.categories.entities.Category;

@Service
public class CategoryService {
    private final CategoryRepository repo;
    private final ModelMapper mapper;

    public CategoryService(CategoryRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<Category> getAll() {
        return this.repo.findAll();
    }

    public Category createCategory(CreateCategoryDTO data) {
        Category category = this.mapper.map(data, Category.class);
        return this.repo.save(category);
    }
}
