package org.example.library.business.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.example.library.jpa.AuthorDbManager;
import org.example.library.jpa.model.Author;
import org.example.library.jpa.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AuthorManagerTest {

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Smith";

    @Mock
    private AuthorDbManager authorDbManager;
    private AutoCloseable mockCloser;

    private AuthorManager sut;

    @BeforeEach
    void setup() {
        mockCloser = MockitoAnnotations.openMocks(this);
        sut = new AuthorManager(authorDbManager);
    }

    @Test
    void testAddAuthor() {
        doReturn(false).when(authorDbManager).authorExists(FIRST_NAME, LAST_NAME);

        boolean author_added = sut.addAuthor(FIRST_NAME, LAST_NAME);

        assertEquals(true, author_added);
    }

    @Test
    void testAddAuthor_alreadyExists() {
        doReturn(true).when(authorDbManager).authorExists(FIRST_NAME, LAST_NAME);

        boolean author_added = sut.addAuthor(FIRST_NAME, LAST_NAME);

        assertEquals(false, author_added);
    }

    @Test
    void testGetBooksByAuthor() {
        Author author = new Author(FIRST_NAME, LAST_NAME);
        Book book_01 = new Book(1234, "What an Absolute Unit", author, new Date());
        Book book_02 = new Book(5678, "The Last of the Units", author, new Date());

        Set<Book> expectedBooks = new LinkedHashSet<>();
        expectedBooks.add(book_01);
        expectedBooks.add(book_02);

        author.setBooks(new ArrayList<>(expectedBooks));

        doReturn(author).when(authorDbManager).readAuthor(FIRST_NAME, LAST_NAME);

        Set<Book> actualBooks = sut.getBooksByAuthor(FIRST_NAME, LAST_NAME);

        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    void testComputeIfAbsent_present() {
        Author expectedAuthor = new Author(FIRST_NAME, LAST_NAME);

        doReturn(true).when(authorDbManager).authorExists(FIRST_NAME, LAST_NAME);
        doReturn(expectedAuthor).when(authorDbManager).readAuthor(FIRST_NAME, LAST_NAME);

        Author acutalAuthor = sut.computeIfAbsent(FIRST_NAME, LAST_NAME);

        assertEquals(expectedAuthor, acutalAuthor);
    }

    @Test
    void testComputeIfAbsent_absent() {
        Author expectedAuthor = new Author(FIRST_NAME, LAST_NAME);

        doReturn(false).when(authorDbManager).authorExists(FIRST_NAME, LAST_NAME);
        doNothing().when(authorDbManager).createAuthor(FIRST_NAME, LAST_NAME);
        doReturn(expectedAuthor).when(authorDbManager).readAuthor(FIRST_NAME, LAST_NAME);

        Author acutalAuthor = sut.computeIfAbsent(FIRST_NAME, LAST_NAME);

        assertEquals(expectedAuthor, acutalAuthor);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockCloser.close();
    }
}
 