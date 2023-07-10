/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  olivier
 * Created: Jun 11, 2023
 */
drop database posting_management_db;
create database posting_management_db;

CREATE TABLE place (
	id serial primary key,
	name character varying(255) NOT NULL,
	province character varying(255) NOT NULL
);
CREATE TABLE employee (
	id serial primary key,
	civility character varying(4) NOT NULL,
	last_name character varying(255) NOT NULL,
	first_name character varying(255) NOT NULL,
	email character varying(255) NOT NULL,
	job character varying(255) NOT NULL,
	place_id integer DEFAULT NULL,
	CONSTRAINT fk_place FOREIGN KEY (place_id) REFERENCES "place"(id)
);
create table posting(
	id serial primary key,
	place_id int,
	employee_id int,
	old_place_id int DEFAULT null,
	posting_date Date not null,
	service_date Date not null,
	CONSTRAINT fk_place foreign key(place_id) references "place"(id) ON DELETE CASCADE,
	CONSTRAINT fk_employee foreign key(employee_id) references "employee"(id),
	CONSTRAINT fk_old_place foreign key(old_place_id) references "place"(id) ON DELETE CASCADE
);