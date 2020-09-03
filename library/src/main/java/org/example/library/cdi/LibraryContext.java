package org.example.library.cdi;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class LibraryContext {

    @PersistenceContext
    private EntityManager authorEntityManager;

    @PersistenceContext
    private EntityManager bookEntityManager;

    @Produces
    @AuthorEntityManager
    public EntityManager getAuthorEntityManager() {
        return authorEntityManager;
    }

    @Produces
    @BookEntityManager
    public EntityManager getBookEntityManager() {
        return bookEntityManager;
    }
}
