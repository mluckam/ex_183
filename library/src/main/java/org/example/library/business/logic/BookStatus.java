package org.example.library.business.logic;

public enum BookStatus {

    ALREADY_ADDED("already added")
    , DOES_NOT_EXIST("does not exist")
    , FAILURE("failure")
    , SUCCESS("success")
    ;

    private final String status;

    private BookStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
