package org.example.library.jpa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

/**
 * The persistent class for the book database table.
 * 
 */
@Entity
@NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b ORDER BY b.title ASC")
@NamedQuery(name = "Book.getBooksWithTitle", query = "SELECT b FROM Book b" + " JOIN FETCH b.authorBean a"
        + " WHERE b.title = :" + BookField.TITLE + " ORDER BY b." + BookField.TITLE + " ASC")
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Positive
    private Integer isbn;

    @NotEmpty
    private String title;

    // bi-directional many-to-one association to Author
    @ManyToOne
    @JoinColumn(name = BookField.AUTHOR)
    // name is the column name in the db
    @Valid
    private Author authorBean;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = BookField.LAST_UPDATED_DB_SYNTAX)
    private Date lastUpdated = new Date();

    public Integer getIsbn() {
        return this.isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthorBean() {
        return this.authorBean;
    }

    public void setAuthorBean(Author authorBean) {
        this.authorBean = authorBean;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}