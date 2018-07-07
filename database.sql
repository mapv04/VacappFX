drop database vacapp;
drop table usuario;
create database vacapp;
use vacapp;

create table usuario(
pk_id_user int not null auto_increment primary key,
name_user nvarchar(30) not null,
last_name nvarchar(30) not null,
username nvarchar(30) not null,
email nvarchar(50) not null,
password_user nvarchar(30) not null,
type_user int not null,
status_user int not null);

INSERT INTO usuario (name_user, last_name, username, email, password_user, type_user, status_user) VALUES
('Miguel','Partida','mapv04','mapv04@gmail.com','qwerty', 0,1),
('Angel','Velasco','asxc','asxc@outlook.com','zxcd', 0,1),
('Oscar','Gonzalez','osc12','oscg@gmail.com','qwerty123', 1,1),
('Fernando','Garcia','garfer','owerg@gmail.com','qwedc', 1,1),
('Alfredo','Ibarra','alfib','alfi@gmail.com','qwertyasd', 2,1),
('Gustavo','Segura','alfhsxb','asji@gmail.com','qwertyassdfd', 2,1);



 CREATE TABLE vac_request(
 pk_id_request int NOT NULL AUTO_INCREMENT, 
 fk_id_user int NOT NULL, 
 name varchar(255), 
 start_date date NOT NULL, 
 end_date date NOT NULL, 
 status varchar(255) NOT NULL,
 supervisor_id int NOT NULL,
 PRIMARY KEY(pk_id_request),
 FOREIGN KEY (fk_id_user) REFERENCES usuario(pk_id_user));
 
  
  INSERT INTO vac_request
 (fk_id_user, name, start_date, end_date, status, supervisor_id)
 VALUES
 (1,'Miguel Partida', '2018-11-01' , '2018-11-07', 'Pending', 3),
 (1,'Miguel Partida','2017-10-01' ,'2017-10-08' , 'Denied', 4),
 (5,'Alfredo Ibarras','2016-09-01' , '2016-09-09', 'Approved', 3),
 (6,'Gustavo Segura', '2017-08-01', '2017-08-10', 'Denied', 4),
 (4,'Fernando Garcia','2017-12-01' ,'2017-12-11' , 'Pending', 4);
 
 
 
CREATE TABLE vac_employee_data(
 pk_id int NOT NULL AUTO_INCREMENT, 
 fk_id_user int NOT NULL, 
 name varchar(255), 
 hired_date date NOT NULL,
 vac_days_available int NOT NULL,
 PRIMARY KEY(pk_id),
 FOREIGN KEY (fk_id_user) REFERENCES usuario(pk_id_user));
 
 
 INSERT INTO vac_employee_data
 (fk_id_user, name, hired_date, vac_days_available)
 VALUES
 (1,'Miguel Partida', '2012-01-01', 10),
 (2,'Angel Velasco', '2013-01-01', 12),
 (3,'Alfredo Ibarras', '2014-01-01', 6),
 (6,'Gustavo Segura', '2015-01-01', 4),
 (4,'Fernando Garcia', '2016-01-01', 6);