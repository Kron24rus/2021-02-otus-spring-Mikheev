insert into genres (id, name) values (0, 'detective');
insert into genres (id, name) values (1, 'comedy');
insert into genres (id, name) values (2, 'criminal');

insert into authors (id, name) values (0, 'AuthorOfDetectiveAndComedy');

insert into books (id, title, author_id, genre_id) values (0, 'comedyBook', 0, 1);
insert into books (id, title, author_id, genre_id) values (1, 'detectiveBook', 0, 0);
insert into books (id, title, author_id, genre_id) values (2, 'criminalBook', 0, 2);

insert into comments (id, text, book_id) values (1, 'test positive comment', 0);
insert into comments (id, text, book_id) values (2, 'test negative comment', 1);
insert into comments (id, text, book_id) values (3, 'test neutral comment', 2);
insert into comments (id, text, book_id) values (4, 'test positive comment', 0);