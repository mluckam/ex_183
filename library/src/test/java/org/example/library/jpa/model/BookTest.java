package org.example.library.jpa.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testIsbnPositive() {
        Author author_01 = new Author(AuthorField.FIRST_NAME_DB_SYNTAX, AuthorField.LAST_NAME_JAVA_SYNTAX);
        Book book_01 = new Book(1234, "What an Absolute Unit", author_01, new Date());

        Set<ConstraintViolation<Book>> violations = validator.validate(book_01);
        assertEquals(violations.size(), 0);

        book_01.setIsbn(-10);
        violations = validator.validate(book_01);
        assertEquals(violations.size(), 1);
    }

    @Test
    void testTitleNotEmpty() {
        Author author_01 = new Author(AuthorField.FIRST_NAME_DB_SYNTAX, AuthorField.LAST_NAME_JAVA_SYNTAX);
        Book book_01 = new Book(1234, "What an Absolute Unit", author_01, new Date());

        Set<ConstraintViolation<Book>> violations = validator.validate(book_01);
        assertEquals(violations.size(), 0);

        book_01.setTitle("");
        violations = validator.validate(book_01);
        assertEquals(violations.size(), 1);
    }

    @Test
    void testDateNotNull() {
        Author author_01 = new Author(AuthorField.FIRST_NAME_DB_SYNTAX, AuthorField.LAST_NAME_JAVA_SYNTAX);
        Book book_01 = new Book(1234, "What an Absolute Unit", author_01, new Date());

        Set<ConstraintViolation<Book>> violations = validator.validate(book_01);
        assertEquals(violations.size(), 0);

        book_01.setLastUpdated(null);
        violations = validator.validate(book_01);
        assertEquals(violations.size(), 1);
    }

    @Test
    void testEquals() {
        Author author_01 = new Author(AuthorField.FIRST_NAME_DB_SYNTAX, AuthorField.LAST_NAME_JAVA_SYNTAX);
        Book book_01 = new Book(1234, "What an Absolute Unit", author_01, new Date());
        Book book_02 = new Book(1234, "What an Absolute Unit", author_01, new Date());

        assertTrue(book_01.equals(book_02));

        book_01.setTitle("A New Title");

        assertFalse(book_01.equals(book_02));
    }

    @Test
    void testToString() {
        String expected_book_string = "{\"isbn\":1234,\"title\":\"What an Absolute Unit\",\"authorBean\":{\"firstName\":\"first_name\",\"lastName\":\"lastName\"}}";
        Author author_01 = new Author(AuthorField.FIRST_NAME_DB_SYNTAX, AuthorField.LAST_NAME_JAVA_SYNTAX);
        Book book_01 = new Book(1234, "What an Absolute Unit", author_01, new Date());

        assertEquals(expected_book_string, book_01.toString());
    }
}
