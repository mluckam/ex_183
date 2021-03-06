package org.example.library.business.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.Date;

import org.example.library.jms.DestinationManager;
import org.example.library.jpa.BookDbManager;
import org.example.library.jpa.model.Author;
import org.example.library.jpa.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BookManagerTest {

    @Mock
    private AuthorManager authorManager;
    @Mock
    private BookDbManager bookDbManager;
    @Mock
    private DestinationManager destinationManager;
    private AutoCloseable mockCloser;

    private BookManager sut;

    @BeforeEach
    void setup() {
        mockCloser = MockitoAnnotations.openMocks(this);
        sut = new BookManager(bookDbManager, authorManager, destinationManager);
    }

    @Test
    void testAddBook() {
        Author author = new Author("John", "Smith");
        Book book = new Book(1234, "The Newest Book", author, new Date());

        doReturn(false).when(bookDbManager).bookExists(book);
        doReturn(author).when(authorManager).computeIfAbsent(author.getFirstName(), author.getLastName());

        BookStatus actualBookStatus = sut.addBook(book);

        assertEquals(BookStatus.SUCCESS, actualBookStatus);
    }

    @Test
    void testAddBook_alreadyExists() {
        Author author = new Author("John", "Smith");
        Book book = new Book(1234, "The Newest Book", author, new Date());

        doReturn(true).when(bookDbManager).bookExists(book);

        BookStatus actualBookStatus = sut.addBook(book);

        assertEquals(BookStatus.ALREADY_ADDED, actualBookStatus);
    }

    @Test
    void testRemoveBook() {
        int isbn = 1234;
        doReturn(1).when(bookDbManager).removeBook(isbn);

        BookStatus actualBookStatus = sut.removeBook(isbn);

        assertEquals(BookStatus.SUCCESS, actualBookStatus);
    }

    @Test
    void testRemoveBook_doesNotExist() {
        int isbn = 1234;
        doReturn(0).when(bookDbManager).removeBook(isbn);

        BookStatus actualBookStatus = sut.removeBook(isbn);

        assertEquals(BookStatus.DOES_NOT_EXIST, actualBookStatus);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockCloser.close();
    }
}
