insert into USERS(id, join_date, name, password, ssn) values (100, now(), 'User1', 'test1111', '125778-1245784');
insert into USERS(id, join_date, name, password, ssn) values (101, now(), 'User2', 'test2222', '125478-1245784');
insert into USERS(id, join_date, name, password, ssn) values (102, now(), 'User3', 'test3333', '125471-1245784');


insert into POST(description, user_id) values ('첫번째 글', 100);
insert into POST(description, user_id) values ('두번째 글', 100);