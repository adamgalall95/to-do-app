package io.nology.to_do_api.categories;

import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import io.nology.to_do_api.categories.entities.Category;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.HashMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CategoryEndToEndTest {

    @LocalServerPort
    private int port;

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryEndToEndTest(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @BeforeEach
    public void setup() {
        RestAssured.port = this.port;
    }

    // get all categories

    @Test
    public void getAllCategories_NoCategoriesInDB_ReturnOkAndEmptyArray() {

        given()
                .when()
                .get("/categories")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(0));
    }

    @Test
    public void getAllCategories_CategoriesInDB_ReturnOkAndArrayOfCategories() {

        Category category1 = new Category();
        category1.setCategoryName("Work");

        categoryRepository.saveAndFlush(category1);

        Category category2 = new Category();
        category2.setCategoryName("Personal");

        categoryRepository.saveAndFlush(category2);

        given()
                .when()
                .get("/categories")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(2))
                .body("categoryName", hasItems("Work", "Personal"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/category-list-schema.json"));
    }

    // get category by id

    @Test
    public void getById_ValidIdForExistingCategory_Success() {

        Category category = new Category();
        category.setCategoryName("Work");

        categoryRepository.saveAndFlush(category);

        given()
                .when()
                .get("/categories/" + category.getCategoryId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("categoryName", equalTo("Work"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/category-schema.json"));
    }

    @Test
    public void getById_IdNotFound() {

        long id = 1;

        given()
                .when()
                .get("/categories/" + id)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(matchesJsonSchemaInClasspath(
                        "schemas/api-error-schema.json"));
    }

    // create category

    @Test
    public void createCategory_ValidDto_Created() {

        HashMap<String, String> data = new HashMap<>();

        data.put("categoryName", "Work");

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/categories")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("categoryName", equalTo("Work"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/category-schema.json"));
    }

    @Test
    public void createCategory_InvalidDto_BadRequest() {

        HashMap<String, String> data = new HashMap<>();

        data.put("categoryName", "");

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/categories")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createCategory_DuplicateCategory_Conflict() {

        Category category = new Category();
        category.setCategoryName("Work");

        categoryRepository.saveAndFlush(category);

        HashMap<String, String> data = new HashMap<>();

        data.put("categoryName", "Work");

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/categories")
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body("message", equalTo("Category 'Work' already exists"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/api-error-schema.json"));
    }
}