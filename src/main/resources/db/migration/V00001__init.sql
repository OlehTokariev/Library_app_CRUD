CREATE TABLE author (
                        id SERIAL PRIMARY KEY,
                        firstName VARCHAR(255) NOT NULL,
                        lastName VARCHAR(255) NOT NULL,
                        birthdate DATE
);

CREATE TABLE book (
                      id SERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      genre VARCHAR(100),
                      year INTEGER,
                      author_id BIGINT NOT NULL,
                      FOREIGN KEY (author_id) REFERENCES author(id)
);
