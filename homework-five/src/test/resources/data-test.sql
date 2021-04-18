insert into genres (id, name) values (0, 'detective');
insert into genres (id, name) values (1, 'comedy');
insert into genres (id, name) values (2, 'criminal');

insert into authors (id, name) values (0, 'AuthorOfDetectiveAndComedy');

insert into books (id, title, author_id, genre_id) values (0, 'comedyBook', 0, 1);
insert into books (id, title, author_id, genre_id) values (1, 'detectiveBook', 0, 0);
insert into books (id, title, author_id, genre_id) values (2, 'criminalBook', 0, 2);