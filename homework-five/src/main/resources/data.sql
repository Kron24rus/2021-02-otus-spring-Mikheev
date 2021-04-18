insert into genres (id, name) values (0, 'detective');
insert into genres (id, name) values (1, 'comedy');
insert into genres (id, name) values (2, 'criminal');
insert into genres (id, name) values (3, 'fantasy');

insert into authors (id, name) values (0, 'AuthorOfDetectiveAndComedy');
insert into authors (id, name) values (1, 'TalentedAuthor');
insert into authors (id, name) values (2, 'BadAndBoringAuthor');

insert into books (id, title, author_id, genre_id) values (0, 'comedyBook', 0, 1);
insert into books (id, title, author_id, genre_id) values (1, 'detectiveBook', 0, 0);
insert into books (id, title, author_id, genre_id) values (2, 'criminalBook', 1, 2);
insert into books (id, title, author_id, genre_id) values (3, 'fantasyBook', 1, 3);
insert into books (id, title, author_id, genre_id) values (4, 'criminalBookOfBadAuthor', 2, 2);
insert into books (id, title, author_id, genre_id) values (5, 'criminalBookOfTalented', 1, 2);
insert into books (id, title, author_id, genre_id) values (6, 'detectiveOfBadAuthor', 2, 0);
insert into books (id, title, author_id, genre_id) values (7, 'comedyOfBoringAuthor', 2, 1);