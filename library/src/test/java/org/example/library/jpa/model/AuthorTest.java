package org.example.library.jpa.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testFirstNameEmpty() {
        Author author = new Author(AuthorField.FIRST_NAME_DB_SYNTAX, AuthorField.LAST_NAME_JAVA_SYNTAX);
        Set<ConstraintViolation<Author>> violations = validator.validate(author);
        assertEquals(violations.size(), 0);

        author.setFirstName("");

        violations = validator.validate(author);
        assertEquals(violations.size(), 1);
    }

    @Test
    void testLastNameEmpty() {
        Author author = new Author(AuthorField.FIRST_NAME_DB_SYNTAX, AuthorField.LAST_NAME_JAVA_SYNTAX);
        Set<ConstraintViolation<Author>> violations = validator.validate(author);
        assertEquals(violations.size(), 0);

        author.setSecondName("");

        violations = validator.validate(author);
        assertEquals(violations.size(), 1);
    }

    @Test
    void testEquals() {
        Author author_01 = new Author(AuthorField.FIRST_NAME_DB_SYNTAX, AuthorField.LAST_NAME_JAVA_SYNTAX);
        Author author_02 = new Author(AuthorField.FIRST_NAME_DB_SYNTAX, AuthorField.LAST_NAME_JAVA_SYNTAX);

        assertTrue(author_01.equals(author_02));

        author_02.setSecondName("");

        assertFalse(author_01.equals(author_02));
    }

    @Test
    void testToString() {
        Author author_01 = new Author(AuthorField.FIRST_NAME_DB_SYNTAX, AuthorField.LAST_NAME_JAVA_SYNTAX);
        String expected_author_string = "{\"firstName\":\"first_name\",\"lastName\":\"lastName\"}";

        author_01.setId(100);
        Book book_01 = new Book(1234, "What an Absolute Unit", author_01, new Date());
        author_01.setBooks(Collections.singletonList(book_01));

        assertEquals(expected_author_string, author_01.toString());
    }
}
