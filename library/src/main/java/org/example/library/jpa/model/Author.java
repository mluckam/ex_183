package org.example.library.jpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

/**
 * The persistent class for the author database table.
 * 
 */
@Entity
@NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a")
public class Author implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = AuthorField.FIRST_NAME_DB_SYNTAX)
    private String firstName;

    @Column(name = AuthorField.LAST_NAME_DB_SYNTAX)
    private String lastName;

    // bi-directional many-to-one association to Book
    @OneToMany(mappedBy = BookField.AUTHOR_BEAN, fetch = FetchType.EAGER)
    //mappedBy is the Book class Author member name
    private List<Book> books;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(@NotEmpty String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setSecondName(String lastName) {
        this.lastName = lastName;
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Book addBook(Book book) {
        getBooks().add(book);
        book.setAuthorBean(this);

        return book;
    }

    public Book removeBook(Book book) {
        getBooks().remove(book);
        book.setAuthorBean(null);

        return book;
    }
}