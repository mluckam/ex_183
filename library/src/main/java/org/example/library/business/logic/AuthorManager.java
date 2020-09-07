package org.example.library.business.logic;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;

import org.example.library.jpa.AuthorDbManager;
import org.example.library.jpa.model.Author;
import org.example.library.jpa.model.Book;

@ApplicationScoped
public class AuthorManager {

    private AuthorDbManager authorDbManager;

    public AuthorManager() {
    }


    @Inject
    public AuthorManager(AuthorDbManager authorDbManager) {
        this.authorDbManager = authorDbManager;
    }

    public boolean addAuthor(@NotEmpty String firstName, @NotEmpty String lastName) {
        if(authorExists(firstName, lastName)) {
            //log statement here
            return false;
        }

        authorDbManager.createAuthor(firstName, lastName);

        return true;
    }

    public Set<Book> getBooksByAuthor(@NotEmpty String firstName, @NotEmpty String lastName) {
        Author author = authorDbManager.readAuthor(firstName, lastName);
        return new LinkedHashSet<>(author.getBooks());
    }

    public boolean authorExists(@NotEmpty String firstName, @NotEmpty String lastName) {
        return authorDbManager.authorExists(firstName, lastName);
    }

    //TODO: there should be a way to do this with less db access
    public Author computeIfAbsent(@NotEmpty String firstName, @NotEmpty String lastName) {
        if(!authorDbManager.authorExists(firstName, lastName)) {
            authorDbManager.createAuthor(firstName, lastName);
        }

        return authorDbManager.readAuthor(firstName,lastName);
    }

    public Set<Author> getAllAuthors() {
        return authorDbManager.getAllAuthors();
    }
}
