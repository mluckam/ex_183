package org.example.library.jpa;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.example.library.cdi.AuthorEntityManager;
import org.example.library.jpa.model.Author;
import org.example.library.jpa.model.AuthorField;
import org.example.library.jpa.model.Book;

@Singleton
public class AuthorDbManager {

    private EntityManager entityManager;

    public AuthorDbManager() {
    }

    @Inject
    public AuthorDbManager(@AuthorEntityManager EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createAuthor(String firstName, String lastName) {
        Author author = new Author(firstName, lastName);
        entityManager.persist(author);
    }

    public Author readAuthor(String firstName, String lastName) {
        TypedQuery<Author> query = entityManager.createQuery(String.format("FROM Author a WHERE"
                + " LOWER(a.firstName) = LOWER(:%s)"
                + " AND LOWER(a.lastName) = LOWER(:%s)"
                , AuthorField.FIRST_NAME_JAVA_SYNTAX, AuthorField.LAST_NAME_JAVA_SYNTAX), Author.class);
        query.setParameter(AuthorField.FIRST_NAME_JAVA_SYNTAX, firstName);
        query.setParameter(AuthorField.LAST_NAME_JAVA_SYNTAX, lastName);

        return query.getSingleResult();
    }

    public boolean authorExists(String firstName, String lastName) {
        TypedQuery<Author> query = entityManager.createQuery(
                String.format("FROM Author a WHERE"
                        + " a.firstName = :%s"
                        + " AND a.lastName = :%s"
                        , AuthorField.FIRST_NAME_JAVA_SYNTAX, AuthorField.LAST_NAME_JAVA_SYNTAX), Author.class);
        query.setParameter(AuthorField.FIRST_NAME_JAVA_SYNTAX, firstName);
        query.setParameter(AuthorField.LAST_NAME_JAVA_SYNTAX, lastName);

        return query.getResultList().size() > 0;
    }

    @SuppressWarnings("unchecked")
    public Set<Author> getAllAuthors() {
        entityManager.createNamedQuery("Book.findAll", Book.class).getResultList();
        Query query = entityManager.createNamedQuery("Author.findAll", Author.class);
        return  (Set<Author>) query.getResultStream().collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
