CREATE TABLE author(id SERIAL PRIMARY KEY UNIQUE NOT null
    , first_name VARCHAR(255) NOT null
    , last_name VARCHAR(255) NOT null
    , CONSTRAINT unique_name UNIQUE(first_name, last_name)
    );

CREATE TABLE book (isbn INTEGER PRIMARY KEY NOT null
    , title VARCHAR(255) NOT null
    , author INTEGER NOT null
    , last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT null
    , FOREIGN KEY (author) REFERENCES AUTHOR(id)
    );
