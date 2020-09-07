package org.example.library.jaxrs;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.example.library.business.logic.AuthorManager;
import org.example.library.business.logic.BookManager;
import org.example.library.jpa.model.Author;
import org.example.library.jpa.model.AuthorField;
import org.example.library.jpa.model.Book;
import org.example.library.jpa.model.BookField;

@Path("/catalog")
public class Catalog {

    static final String HELLO_WORLD = "Hello World!";
    static final String ADD_BOOK_JSON_FORMAT = "{addBook: %s}";
    static final String REMOVE_BOOK_JSON_FORMAT = "{removeBook: %s}";

    private BookManager bookManager;
    private AuthorManager authorManager;

    public Catalog() {
    }

    @Inject
    public Catalog(BookManager bookManager, AuthorManager authorManager) {
        this.bookManager = bookManager;
        this.authorManager = authorManager;
    }

    @GET
    @Path("/helloWorld")
    public String helloWorld() {
        return HELLO_WORLD;
    }

    @PUT
    @Path("/addBook")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public String addBook(Book book) {
        String bookAdded = bookManager.addBook(book).getStatus();
        return String.format(ADD_BOOK_JSON_FORMAT, bookAdded);
    }

    @DELETE
    @Path("/removeBook")
    public String removeBook(@QueryParam(BookField.ISBN) int isbn) {
        String bookRemoved = bookManager.removeBook(isbn).getStatus();
        return String.format(REMOVE_BOOK_JSON_FORMAT, bookRemoved);
    }

    @GET
    @Path("/showBooks")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Book> showBooks() {
        return bookManager.getAllBooks();
    }

    @GET
    @Path("/showAuthors")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Author> showAuthors() {
        return authorManager.getAllAuthors();
    }

    @GET
    @Path("/searchByAuthor")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Book> searchByAuthor(@QueryParam(AuthorField.FIRST_NAME_JAVA_SYNTAX) String firstName,
            @QueryParam(AuthorField.LAST_NAME_JAVA_SYNTAX) String lastName) {
        return authorManager.getBooksByAuthor(firstName, lastName);
    }

    @GET
    @Path("/searchByTitle")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByTitle(@QueryParam(BookField.TITLE) String title) {
        return Response.status(Status.OK)
        .entity(bookManager.getBooksWithTitle(title))
        .build();
    }
}
