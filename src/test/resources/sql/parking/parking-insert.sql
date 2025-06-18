insert into users (id, username, password, role)
values (100, 'ana@email.com', '$2a$12$0GR0rzDEC3I4udi3/waAhe0kspNSQU/5Cg2mGBjYRxtPaxSP5Exi6', 'ROLE_ADMIN');

insert into users (id, username, password, role)
values (101, 'jorge@email.com', '$2a$12$0GR0rzDEC3I4udi3/waAhe0kspNSQU/5Cg2mGBjYRxtPaxSP5Exi6', 'ROLE_CUSTOMER');

insert into users (id, username, password, role)
values (102, 'test@email.com', '$2a$12$0GR0rzDEC3I4udi3/waAhe0kspNSQU/5Cg2mGBjYRxtPaxSP5Exi6', 'ROLE_CUSTOMER');

insert into parking_space (id, code, status)
values(10, 'A-01', 'FREE');

insert into parking_space (id, code, status)
values(20, 'A-02', 'FREE');

insert into parking_space (id, code, status)
values(30, 'A-03', 'BUSY');

insert into parking_space (id, code, status)
values(40, 'A-04', 'FREE');