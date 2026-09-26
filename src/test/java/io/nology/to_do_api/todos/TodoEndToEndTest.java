package io.nology.to_do_api.todos;

import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import io.nology.to_do_api.categories.CategoryRepository;
import io.nology.to_do_api.categories.entities.Category;
import io.nology.to_do_api.todos.entities.Todo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.HashMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TodoEndToEndTest {

    @LocalServerPort
    private int port;

    private TodoRepository todoRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public TodoEndToEndTest(
            TodoRepository todoRepository,
            CategoryRepository categoryRepository) {

        this.todoRepository = todoRepository;
        this.categoryRepository = categoryRepository;
    }

    @BeforeEach
    public void setup() {
        RestAssured.port = this.port;
    }

    // get all todos

    @Test
    public void getAllTodos_NoTodosInDB_ReturnOkAndEmptyArray() {

        given()
                .when()
                .get("/todos")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(0));
    }

    @Test
    public void getAllTodos_TodosInDB_ReturnOkAndArrayOfTodos() {

        // arrange
        Todo todo1 = new Todo();
        todo1.setTask("Buy groceries");
        todo1.setCategoryId(1);
        todo1.setCompleted(false);

        todoRepository.saveAndFlush(todo1);

        Todo todo2 = new Todo();
        todo2.setTask("Finish testing");
        todo2.setCategoryId(2);
        todo2.setCompleted(true);

        todoRepository.saveAndFlush(todo2);

        given()
                .when()
                .get("/todos")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(2))
                .body("task", hasItems("Buy groceries", "Finish testing"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/todo-list-schema.json"));
    }

    // get all todos by category

    @Test
    public void getAllTodos_ByCategory_ReturnOkAndTodosInCategory() {

        // arrange
        Todo todo1 = new Todo();
        todo1.setTask("Buy groceries");
        todo1.setCategoryId(1);
        todo1.setCompleted(false);

        todoRepository.saveAndFlush(todo1);

        Todo todo2 = new Todo();
        todo2.setTask("Finish testing");
        todo2.setCategoryId(1);
        todo2.setCompleted(true);

        todoRepository.saveAndFlush(todo2);

        Todo todo3 = new Todo();
        todo3.setTask("Go to gym");
        todo3.setCategoryId(2);
        todo3.setCompleted(false);

        todoRepository.saveAndFlush(todo3);

        given()
                .when()
                .get("/todos?categoryId=1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(2))
                .body("task", hasItems("Buy groceries", "Finish testing"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/todo-list-schema.json"));
    }

    // get todo by id

    @Test
    public void getById_ValidIdForExistingTodo_Success() {

        Todo todo = new Todo();
        todo.setTask("Buy groceries");
        todo.setCategoryId(1);
        todo.setCompleted(false);

        todoRepository.saveAndFlush(todo);

        given()
                .when()
                .get("/todos/" + todo.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("task", equalTo(todo.getTask()))
                .body("categoryId", equalTo((int) todo.getCategoryId()))
                .body("completed", equalTo(todo.isCompleted()))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/todo-schema.json"));
    }

    @Test
    public void getById_IdNotFound() {

        long id = 1;

        given()
                .when()
                .get("/todos/" + id)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Todo with id 1 was not found"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/api-error-schema.json"));
    }

    @Test
    public void getById_InvalidId_BadRequest() {

        given()
                .when()
                .get("/todos/hello")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(matchesJsonSchemaInClasspath(
                        "schemas/api-error-schema.json"));
    }

    // create todo

    @Test
    public void createTodo_InvalidDto_BadRequest() {

        HashMap<String, Object> data = new HashMap<>();

        data.put("task", "");

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/todos")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createTodo_NoBody_BadRequest() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .post("/todos")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createTodo_CategoryNotFound_NotFound() {

        HashMap<String, Object> data = new HashMap<>();

        data.put("task", "Buy groceries");
        data.put("categoryId", 999);
        data.put("completed", false);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/todos")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message",
                        equalTo("Category with id 999 was not found"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/api-error-schema.json"));
    }

    @Test
    public void createTodo_ValidDto_Created() {

        Category category = new Category();
        category.setCategoryName("Work");

        categoryRepository.saveAndFlush(category);

        HashMap<String, Object> data = new HashMap<>();

        data.put("task", "Buy groceries");
        data.put("categoryId", category.getCategoryId());
        data.put("completed", false);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/todos")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("task", equalTo("Buy groceries"))
                .body("completed", equalTo(false))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/todo-schema.json"));
    }

    @Test
    public void updateTodo_IdNotFound_NotFound() {

        HashMap<String, Object> data = new HashMap<>();

        data.put("task", "Updated task");

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .patch("/todos/999")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message",
                        equalTo("Todo with id 999 was not found"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/api-error-schema.json"));
    }

    @Test
    public void updateTodo_CategoryNotFound_NotFound() {

        Todo todo = new Todo();
        todo.setTask("Old task");
        todo.setCategoryId(1);
        todo.setCompleted(false);

        todoRepository.saveAndFlush(todo);

        HashMap<String, Object> data = new HashMap<>();

        data.put("categoryId", 999);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .patch("/todos/" + todo.getId())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message",
                        equalTo("Category with id 999 was not found"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/api-error-schema.json"));
    }

    @Test
    public void updateTodo_ValidDto_Success() {

        Category category = new Category();
        category.setCategoryName("Work");

        categoryRepository.saveAndFlush(category);

        Todo todo = new Todo();
        todo.setTask("Old task");
        todo.setCategoryId(category.getCategoryId());
        todo.setCompleted(false);

        todoRepository.saveAndFlush(todo);

        HashMap<String, Object> data = new HashMap<>();

        data.put("task", "Updated task");
        data.put("categoryId", category.getCategoryId());
        data.put("completed", true);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .patch("/todos/" + todo.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("task", equalTo("Updated task"))
                .body("completed", equalTo(true))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/todo-schema.json"));
    }

    // delete todo

    @Test
    public void deleteTodo_IdNotFound_NotFound() {

        given()
                .when()
                .delete("/todos/999")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message",
                        equalTo("Todo with id 999 was not found"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/api-error-schema.json"));
    }

    @Test
    public void deleteTodo_ValidId_NoContent() {

        Todo todo = new Todo();
        todo.setTask("Buy groceries");
        todo.setCategoryId(1);
        todo.setCompleted(false);

        todoRepository.saveAndFlush(todo);

        given()
                .when()
                .delete("/todos/" + todo.getId())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}