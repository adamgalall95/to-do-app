package io.nology.to_do_api.categories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import io.nology.to_do_api.categories.dtos.CreateCategoryDTO;
import io.nology.to_do_api.categories.entities.Category;
import io.nology.to_do_api.common.exceptions.DuplicateCategoryException;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

        @Mock
        private CategoryRepository repo;

        @Mock
        private ModelMapper mapper;

        @InjectMocks
        private CategoryService service;

        // 1. getAll

        @Test
        void getAll_CallsFindAll() {

                Category category1 = new Category();
                Category category2 = new Category();

                when(repo.findAll())
                                .thenReturn(List.of(category1, category2));

                List<Category> result = service.getAll();

                assertEquals(2, result.size());
                verify(repo).findAll();
        }

        // 2. createCategory - valid

        @Test
        void createCategory_WhenNameDoesNotExist_SavesCategory() {

                CreateCategoryDTO dto = new CreateCategoryDTO();
                dto.setCategoryName("Work");

                Category category = new Category();
                category.setCategoryName("Work");

                when(repo.existsByCategoryName("Work"))
                                .thenReturn(false);

                when(mapper.map(dto, Category.class))
                                .thenReturn(category);

                when(repo.save(category))
                                .thenReturn(category);

                Category result = service.createCategory(dto);

                assertEquals(category, result);
                assertEquals("Work", result.getCategoryName());

                verify(repo).existsByCategoryName("Work");
                verify(mapper).map(dto, Category.class);
                verify(repo).save(category);
        }

        // 3. createCategory - duplicate

        @Test
        void createCategory_WhenNameAlreadyExists_ThrowsDuplicateCategoryException() {

                CreateCategoryDTO dto = new CreateCategoryDTO();
                dto.setCategoryName("Work");

                when(repo.existsByCategoryName("Work"))
                                .thenReturn(true);

                assertThrows(
                                DuplicateCategoryException.class,
                                () -> service.createCategory(dto));

                verify(repo).existsByCategoryName("Work");
                verify(mapper, never()).map(any(), eq(Category.class));
                verify(repo, never()).save(any(Category.class));
        }

        // 4. getCategoryById

        // 4. getCategoryById

        @Test
        void getCategoryById_CallsFindById() {

                Long id = 1L;

                Category category = new Category();
                category.setCategoryId(id);

                when(repo.findById(id))
                                .thenReturn(Optional.of(category));

                Category result = service.getCategoryById(id);

                assertEquals(category, result);

                verify(repo).findById(id);
        }
}