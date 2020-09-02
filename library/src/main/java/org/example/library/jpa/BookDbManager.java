package org.example.library.jpa;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.example.library.jpa.model.Book;
import org.example.library.jpa.model.BookField;

@Singleton
public class BookDbManager {

    @PersistenceContext
    private EntityManager entityManager;

    public void addBook(Book book) {
        entityManager.merge(book.getAuthorBean());
        entityManager.persist(book);
    }

    public int removeBook(int isbn) {
        Query query = entityManager.createQuery("DELETE FROM Book b WHERE b.isbn = ?1");
        query.setParameter(1, BookField.ISBN);

        return query.executeUpdate();
    }

    public boolean bookExists(Book book) {
        TypedQuery<Book> query = entityManager.createQuery(String.format("FROM Book b WHERE"
                + " b.isbn = :%s"
                + " AND b.title = :%s"
                , BookField.ISBN, BookField.TITLE), Book.class);
        query.setParameter(BookField.ISBN, book.getIsbn());
        query.setParameter(BookField.TITLE, book.getTitle());

        return query.getResultList().size() > 0;
    }

    public Set<Book> getBooksWithTitle(String title) {
        TypedQuery<Book> query = entityManager.createNamedQuery("Book.getBooksWithTitle", Book.class);
        query.setParameter(BookField.TITLE, title);

        return (Set<Book>) query.getResultStream().collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @SuppressWarnings("unchecked")
    public Set<Book> getAllBooks() {
        Query query = entityManager.createNamedQuery("Book.findAll", Book.class);
        return  (Set<Book>) query.getResultStream().collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
