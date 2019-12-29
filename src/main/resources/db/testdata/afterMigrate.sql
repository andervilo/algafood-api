delete
from restaurant_payment_method;
delete
from payment_method;
delete
from product;
delete
from restaurant;
delete
from cuisine;
delete
from city;
delete
from province;

delete
from user_group;
delete
from group_permission;
delete
from user;
delete
from permission;
delete
from group_;

insert into cuisine (name)
values ('Italian');
insert into cuisine (name)
values ('Brazilian');

insert into payment_method (description)
values ('Credit Card');
insert into payment_method (description)
values ('Cash');
insert into payment_method (description)
values ('Word');

insert into permission (name, description)
values ('READ', 'READ records');
insert into permission (name, description)
values ('WRITE', 'READ and WRITE records');

insert into group_ (name)
values ('Group 1');
insert into group_ (name)
values ('Group 2');

insert into group_permission (group_id, permission_id)
values ((select id from group_ where name = 'Group 1'), (select id from permission where name = 'READ'));
insert into group_permission (group_id, permission_id)
values ((select id from group_ where name = 'Group 2'), (select id from permission where name = 'READ'));
insert into group_permission (group_id, permission_id)
values ((select id from group_ where name = 'Group 2'), (select id from permission where name = 'WRITE'));

insert into user (name, email, password, created_date)
values ('drugowick', 'bruno.drugowick@gmail.com', 'password', current_timestamp);
insert into user (name, email, password, created_date)
values ('drugowick2', 'brunodrugowick@gmail.com', 'password', current_timestamp);

insert into province (name, abbreviation)
values ('São Paulo', 'SP');
insert into province (name, abbreviation)
values ('Minas Gerais', 'MG');
insert into province (name, abbreviation)
values ('Santa Catarina', 'SC');
insert into province (name, abbreviation)
values ('Rio Grande do Sul', 'RS');

insert into city (name, province_id)
values ('Campinas', (select id from province where name = 'São Paulo'));
insert into city (name, province_id)
values ('São José dos Campos', (select id from province where name = 'São Paulo'));
insert into city (name, province_id)
values ('Ribeirão Preto', (select id from province where name = 'São Paulo'));
insert into city (name, province_id)
values ('Belo Horizonte', (select id from province where name = 'Minas Gerais'));
insert into city (name, province_id)
values ('Florianópolis', (select id from province where name = 'Santa Catarina'));
insert into city (name, province_id)
values ('Porto Alegre', (select id from province where name = 'Rio Grande do Sul'));

insert into restaurant (address_address_line_1, address_address_line_2, address_postal_code, address_region,
                        delivery_fee, name, created_date, updated_date, address_city_id, cuisine_id)
values ('Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', 10.00, 'Pizzaria Marcante', current_timestamp,
        current_timestamp, (select id from city where name = 'Campinas'),
        (select id from cuisine where name = 'Italian'));
insert into restaurant (address_address_line_1, address_address_line_2, address_postal_code, address_region,
                        delivery_fee, name, created_date, updated_date, address_city_id, cuisine_id)
values ('Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', 5.00, 'Bar Preste Atenção', current_timestamp,
        current_timestamp, (select id from city where name = 'São José dos Campos'),
        (select id from cuisine where name = 'Brazilian'));
insert into restaurant (address_address_line_1, address_address_line_2, address_postal_code, address_region,
                        delivery_fee, name, created_date, updated_date, address_city_id, cuisine_id)
values ('Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', 10.00, 'Pizzaria Embarcante', current_timestamp,
        current_timestamp, (select id from city where name = 'São José dos Campos'),
        (select id from cuisine where name = 'Italian'));
insert into restaurant (address_address_line_1, address_address_line_2, address_postal_code, address_region,
                        delivery_fee, name, created_date, updated_date, address_city_id, cuisine_id)
values ('Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', 0.00, 'Mexican Crazy Hat Food', current_timestamp,
        current_timestamp, (select id from city where name = 'Belo Horizonte'),
        (select id from cuisine where name = 'Brazilian'));

INSERT INTO product (active, description, name, price, restaurant_id)
VALUES (1, 'Delicious potato', 'Potato', 25.00, (select id from restaurant where name = 'Pizzaria Marcante'));
INSERT INTO product (active, description, name, price, restaurant_id)
VALUES (1, 'Nhami', 'Nhami', 5.00, (select id from restaurant where name = 'Bar Preste Atenção'));
INSERT INTO product (active, description, name, price, restaurant_id)
VALUES (1, 'Burger Mara', 'Burger', 8.00, (select id from restaurant where name = 'Pizzaria Embarcante'));
INSERT INTO product (active, description, name, price, restaurant_id)
VALUES (1, 'Chocolate', 'Chocolate', 3.00, (select id from restaurant where name = 'Mexican Crazy Hat Food'));

insert into restaurant_payment_method (restaurant_id, payment_method_id)
values ((select id from restaurant where name = 'Pizzaria Marcante'),
        (select id from payment_method where description = 'Credit Card')),
       ((select id from restaurant where name = 'Pizzaria Marcante'),
        (select id from payment_method where description = 'Cash')),
       ((select id from restaurant where name = 'Bar Preste Atenção'),
        (select id from payment_method where description = 'Credit Card')),
       ((select id from restaurant where name = 'Pizzaria Embarcante'),
        (select id from payment_method where description = 'Credit Card')),
       ((select id from restaurant where name = 'Pizzaria Embarcante'),
        (select id from payment_method where description = 'Cash')),
       ((select id from restaurant where name = 'Pizzaria Embarcante'),
        (select id from payment_method where description = 'Word'));