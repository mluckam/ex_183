package org.example.library.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import java.util.List;

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
    @XmlTransient
    @ToStringExclude
    private Integer id;

    @Column(name = AuthorField.FIRST_NAME_DB_SYNTAX)
    @NotEmpty
    private String firstName;

    @Column(name = AuthorField.LAST_NAME_DB_SYNTAX)
    @NotEmpty
    private String lastName;

    // bi-directional many-to-one association to Book
    @OneToMany(mappedBy = BookField.AUTHOR_BEAN, fetch = FetchType.EAGER)
    //mappedBy is the Book class Author member name
    @JsonIgnoreProperties(BookField.AUTHOR_BEAN)
    @Valid
    @ToStringExclude
    @EqualsExclude
    @HashCodeExclude
    private List<Book> books;

    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

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

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, false);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}