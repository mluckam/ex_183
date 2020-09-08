package org.example.library.business.logic;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.example.library.jms.DestinationManager;
import org.example.library.jpa.BookDbManager;
import org.example.library.jpa.model.Author;
import org.example.library.jpa.model.Book;
import org.jboss.logging.Logger;

@ApplicationScoped
public class BookManager {

    private static final Logger LOGGER = Logger.getLogger(BookManager.class);

    private BookDbManager bookDbManager;
    private AuthorManager authorManager;
    private DestinationManager destinationManager;

    public BookManager() {
    }

    @Inject
    public BookManager(BookDbManager bookDbManager, AuthorManager authorManager, DestinationManager destinationManager) {
        this.bookDbManager = bookDbManager;
        this.authorManager = authorManager;
        this.destinationManager = destinationManager;
    }

    public BookStatus addBook(Book book) {
        try {
            if (bookDbManager.bookExists(book)) {
                LOGGER.infof("book '%s' was added to catalog, but it already exists.", book);
                return BookStatus.ALREADY_ADDED;
            }
            Author author = authorManager.computeIfAbsent(book.getAuthorBean().getFirstName(),
                    book.getAuthorBean().getLastName());
            book.setAuthorBean(author);
            bookDbManager.addBook(book);
        } catch (Exception e) {
            LOGGER.error("failed to add book to catalog", e);
            return BookStatus.FAILURE;
        }

        destinationManager.sendToSuccessQueue(String.format("Book %s was added to the catalog.", book));
        return BookStatus.SUCCESS;
    }

    public BookStatus removeBook(@Positive int isbn) {
        int numberOfEntiresRemoved = 0;
        try {
        numberOfEntiresRemoved = bookDbManager.removeBook(isbn);
        } catch(Exception e) {
            LOGGER.error("failed to remove book from catalog", e);
            return BookStatus.FAILURE;
        }

        return numberOfEntiresRemoved > 0 ? BookStatus.SUCCESS : BookStatus.DOES_NOT_EXIST;
    }

    public Set<Book> getBooksWithTitle(@NotEmpty String title) {
        return bookDbManager.getBooksWithTitle(title);
    }

    public Set<Book> getAllBooks() {
        return bookDbManager.getAllBooks();
    }
}
