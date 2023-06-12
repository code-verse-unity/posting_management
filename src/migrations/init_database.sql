/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  olivier
 * Created: Jun 11, 2023
 */

create database posting_management_db;

drop  database posting_management_db;

create table "place"(
	id serial primary key,
	name varchar(255) not null,
	province varchar() not null
);

create table "employee"(
	id serial primary key,
	civility varchar(4) not null,
	last_name varchar(255) not null,
	first_name varchar(255) not null,
	email  varchar(255) not null
);

create table posting(
	id serial primary key,
	place_id int,
	employee_id int,
	old_place_id int not null,
	posting_date Date not null,
	service_date Date not null,
	CONSTRAINT fk_place
		foreign key(place_id)
			references "place"(id),
	CONSTRAINT fk_employee
		foreign key(employee_id)
			references "employee"(id),
	CONSTRAINT fk_old_place
		foreign key(old_place_id)
			references "place"(id)
);