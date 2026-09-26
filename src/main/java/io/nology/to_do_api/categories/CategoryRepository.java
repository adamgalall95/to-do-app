package io.nology.to_do_api.categories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.nology.to_do_api.categories.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByCategoryName(String categoryName);
}
