insert into users (id, username, password, role)
values (100, 'ana@email.com', '$2a$12$0GR0rzDEC3I4udi3/waAhe0kspNSQU/5Cg2mGBjYRxtPaxSP5Exi6', 'ROLE_ADMIN');

insert into users (id, username, password, role)
values (101, 'jorge@email.com', '$2a$12$0GR0rzDEC3I4udi3/waAhe0kspNSQU/5Cg2mGBjYRxtPaxSP5Exi6', 'ROLE_CUSTOMER');

insert into users (id, username, password, role)
values (102, 'test@email.com', '$2a$12$0GR0rzDEC3I4udi3/waAhe0kspNSQU/5Cg2mGBjYRxtPaxSP5Exi6', 'ROLE_CUSTOMER');

insert into users (id, username, password, role)
values (103, 'toby@email.com', '$2a$12$0GR0rzDEC3I4udi3/waAhe0kspNSQU/5Cg2mGBjYRxtPaxSP5Exi6', 'ROLE_CUSTOMER');

insert into users (id, username, password, role)
values (104, 'argolo@email.com', '$2a$12$0GR0rzDEC3I4udi3/waAhe0kspNSQU/5Cg2mGBjYRxtPaxSP5Exi6', 'ROLE_ADMIN');

insert into customers(id, name, cpf, id_user) values (20, 'Jorge Roberto Argolo', '68788268020', 101);
/*insert into customers(id, name, cpf, id_user) values (50, 'Roberto Argolo', '63332538047', 104);*/
insert into customers(id, name, cpf, id_user) values (50, 'Roberto Argolo', '63332538047', 103);