package org.example.library.business.logic;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.example.library.jpa.BookDbManager;
import org.example.library.jpa.model.Author;
import org.example.library.jpa.model.Book;

@ApplicationScoped
public class BookManager {

    private BookDbManager bookDbManager;
    private AuthorManager authorManager;

    public BookManager() {
    }

    @Inject
    public BookManager(BookDbManager bookDbManager, AuthorManager authorManager) {
        this.bookDbManager = bookDbManager;
        this.authorManager = authorManager;
    }

    public boolean addBook(Book book) {
        if(bookDbManager.bookExists(book)) {
            //add log statement
            return false;
        }

        Author author = authorManager.computeIfAbsent(book.getAuthorBean().getFirstName(), book.getAuthorBean().getLastName());
        book.setAuthorBean(author);

        bookDbManager.addBook(book);

        return true;
    }

    public boolean removeBook(@Positive int isbn) {
        int numberOfEntiresRemoved = bookDbManager.removeBook(isbn);

        return numberOfEntiresRemoved > 0;
    }

    public Set<Book> getBooksWithTitle(@NotEmpty String title) {
        return bookDbManager.getBooksWithTitle(title);
    }

    public Set<Book> getAllBooks() {
        return bookDbManager.getAllBooks();
    }
}
