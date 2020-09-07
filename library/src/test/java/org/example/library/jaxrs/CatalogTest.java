package org.example.library.jaxrs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.example.library.business.logic.AuthorManager;
import org.example.library.business.logic.BookManager;
import org.example.library.business.logic.BookStatus;
import org.example.library.jpa.model.Author;
import org.example.library.jpa.model.Book;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.SingletonResource;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.spi.metadata.DefaultResourceClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CatalogTest {

    @Mock
    private AuthorManager authorManager;
    @Mock
    private BookManager bookManager;
    private AutoCloseable mockCloser;

    private Catalog sut;

    private Dispatcher dispatcher;

    @BeforeEach
    void setup() {
        mockCloser = MockitoAnnotations.openMocks(this);
        sut = new Catalog(bookManager, authorManager);

        dispatcher = MockDispatcherFactory.createDispatcher();
        ResourceFactory resourceFactory = new SingletonResource(sut, new DefaultResourceClass(Catalog.class, null));
        dispatcher.getRegistry().addResourceFactory(resourceFactory);
    }

    @Test
    void testHelloWorld() throws UnsupportedEncodingException, URISyntaxException {

        MockHttpRequest request = MockHttpRequest.get("/catalog/helloWorld");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals(Catalog.HELLO_WORLD, response.getContentAsString());
    }

    
    @ParameterizedTest
    @EnumSource(value = BookStatus.class , names = {"SUCCESS", "ALREADY_ADDED", "FAILURE"})
    void testAddBook(BookStatus expectedStatus) {
        String expectedResult = String.format(Catalog.ADD_BOOK_JSON_FORMAT, expectedStatus.getStatus());
        Book book = new Book();
        doReturn(expectedStatus).when(bookManager).addBook(book);

        String actualResult = sut.addBook(book);

        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @EnumSource(value = BookStatus.class , names = {"SUCCESS", "DOES_NOT_EXIST", "FAILURE"})
    void testRemoveBook(BookStatus expectedStatus) {
        String expectedResult = String.format(Catalog.REMOVE_BOOK_JSON_FORMAT, expectedStatus.getStatus());
        int isbn = 1234;
        doReturn(expectedStatus).when(bookManager).removeBook(isbn);

        String actualResult = sut.removeBook(isbn);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testShowAllBooks() {
        Author author = new Author("John", "Smith");
        Book book_01 = new Book(1234, "What an Absolute Unit", author, new Date());
        Book book_02 = new Book(5678, "The Last of the Units", author, new Date());

        Set<Book> expectedBooks = new LinkedHashSet<>();
        expectedBooks.add(book_01);
        expectedBooks.add(book_02);

        doReturn(expectedBooks).when(bookManager).getAllBooks();

        Set<Book> actualBooks = sut.showBooks();

        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    void testShowAllAuthors() {
        Author author_01 = new Author("John", "Smith");
        Author author_02 = new Author("Leeroy", "Jenkins");

        Set<Author> expectedAuthors = new LinkedHashSet<>();
        expectedAuthors.add(author_01);
        expectedAuthors.add(author_02);

        doReturn(expectedAuthors).when(authorManager).getAllAuthors();

        Set<Author> actualAuthors = sut.showAuthors();

        assertEquals(expectedAuthors, actualAuthors);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockCloser.close();
    }
}
