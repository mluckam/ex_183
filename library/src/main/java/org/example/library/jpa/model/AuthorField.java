package org.example.library.jpa.model;

public final class AuthorField {

    private AuthorField() {
    }

    public static final String FIRST_NAME_DB_SYNTAX = "first_name";
    public static final String LAST_NAME_DB_SYNTAX = "last_name";

    public static final String FIRST_NAME_JAVA_SYNTAX = "firstName";
    public static final String LAST_NAME_JAVA_SYNTAX = "lastName";

    public static final String BOOKS = "books";
}
