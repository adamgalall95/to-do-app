package io.nology.to_do_api.categories;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.nology.to_do_api.categories.dtos.CreateCategoryDTO;
import io.nology.to_do_api.categories.entities.Category;
import io.nology.to_do_api.common.exceptions.DuplicateCategoryException;
import io.nology.to_do_api.common.exceptions.NotFoundException;

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

        boolean categoryExists = this.repo.existsByCategoryName(
                data.getCategoryName());

        if (categoryExists) {
            throw new DuplicateCategoryException(
                    data.getCategoryName());
        }
        Category category = this.mapper.map(data, Category.class);
        return this.repo.save(category);
    }

    public Category getCategoryById(Long id) {
        return this.repo.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Category with id " + id + " was not found"));
    }
}
