drop database vacapp;
create database vacapp;
use vacapp;

create table usuario(
pk_id_user int not null auto_increment primary key,
name_user varchar(30) not null,
last_name varchar(30) not null,
username varchar(30) not null,
email varchar(50) not null,
password_user varchar(30) not null,
type_user int not null,
status_user int not null,
question varchar(30) not null,
answer varchar(30) not null);

INSERT INTO usuario (name_user, last_name, username, email, password_user, type_user, status_user, question, answer) VALUES
("Jorge ","C","ceo","owner@ceo.com","ceo",1,1,3,'UNIVA'),
('Miguel','Partida','mapv04','mapv04@gmail.com','qwerty', 0,1,3,'UNIVA'),
('Angel','Velasco','asxc','asxc@outlook.com','zxcd', 0,1,3,'UNIVA'),
('Oscar','Gonzalez','osc12','oscg@gmail.com','qwerty123', 1,1,3,'UNIVA'),
('Fernando','Garcia','garfer','owerg@gmail.com','qwedc', 1,1,3,'UNIVA'),
('Alfredo','Ibarra','alfib','alfi@gmail.com','qwertyasd', 2,1,3,'UNIVA'),
('Gustavo','Segura','alfhsxb','asji@gmail.com','qwertyassdfd', 2,1,3,'UNIVA'),
('Eduardo','Ramirez','edu','edurai@gmail.com','qwertyasde', 2,1,3,'UNIVA'),
('Ruben','Ochoa','rub123','ruben.och@gmail.com','qwertyasasd', 2,1,3,'UNIVA'),
('Ivan','Martinez','ivvan','martinez.iv@gmail.com','qwesxasasd', 2,1,3,'UNIVA'),
('Gerardo','Contreras','gera12','gerardo12@gmail.com','asdfcx', 2,1,3,'UNIVA'),
('Erika','Preciado','erk13','preciado.erk@gmail.com','qwsxdc', 2,1,3,'UNIVA');


 CREATE TABLE vac_request(
 pk_id_request int NOT NULL AUTO_INCREMENT, 
 fk_id_user int NOT NULL, 
 start_date date NOT NULL, 
 end_date date NOT NULL, 
 status varchar(255) NOT NULL,
 supervisor_id int NOT NULL,
 PRIMARY KEY(pk_id_request),
 FOREIGN KEY (fk_id_user) REFERENCES usuario(pk_id_user));
 
  
 INSERT INTO vac_request
 (fk_id_user, start_date, end_date, status, supervisor_id)
 VALUES
 (1, '2018-11-01' , '2018-11-07', 'Pending', 3),
 (1,'2017-10-01' ,'2017-10-08' , 'Denied', 4),
 (5,'2016-09-01' , '2016-09-09', 'Approved', 3),
 (6, '2017-08-01', '2017-08-10', 'Denied', 4),
 (4,'2017-12-01' ,'2017-12-11' , 'Pending', 4);
 
 
 
CREATE TABLE vac_employee_data(
 pk_id int NOT NULL AUTO_INCREMENT, 
 fk_id_user int NOT NULL, 
 hired_date date NOT NULL,
 vac_days_available int NOT NULL,
 PRIMARY KEY(pk_id),
 FOREIGN KEY (fk_id_user) REFERENCES usuario(pk_id_user));
 
 
 INSERT INTO vac_employee_data
 (fk_id_user, hired_date, vac_days_available)
 VALUES
 (1, '2010-01-01', 100),
 (2, '2013-01-01', 12),
 (3, '2014-01-01', 10),
 (4, '2016-01-01', 6),
 (5, '2015-01-01', 6),
 (6, '2017-06-06', 10),
 (7, '2013-05-05', 12),
 (8, '2018-01-01', 10),
 (9, '2012-02-02', 6),
 (10, '2014-03-03', 6),
 (11, '2015-08-08', 6),
 (12, '2014-08-08', 6);


 
 create table workgroup(
pk_workgroup_id int not null auto_increment primary key,
workgroup_name nvarchar(50) not null,
fk_leader_id int not null,
leader_name nvarchar(100) not null,
created_date date not null,
workgroup_status int not null,
foreign key (fk_leader_id) references Usuario(pk_id_user));


insert into workgroup(workgroup_name, fk_leader_id,leader_name,created_date, workgroup_status) values
('WorkGroup 1',3,'Oscar Gonzalez','2016-02-02',1),
('WorkGroup 2',4,'Fernando Garcia','2017-05-01',1);


create table workgroup_data(
fk_workgroup_id int not null,
fk_usuario_id int not null,
employee_name nvarchar(100) not null,
added_date date not null,
foreign key (fk_workgroup_id) references workgroup(pk_workgroup_id),
foreign key (fk_usuario_id) references usuario(pk_id_user));

insert into workgroup_data(fk_workgroup_id, fk_usuario_id,employee_name,added_date) values
(1,5,'Alfredo Ibarra','2016-03-03'),
(1,6,'Gustavo Segura','2016-03-03'),
(1,7,'Eduardo Ramirez','2016-03-03'),
(2,8,'Ruben Ochoa','2017-05-02'),
(2,9,'Ivan Martinez','2017-05-02'),
(2,10,'Gerardo Contreras','2017-05-02'),
(2,11,'Erika Preciado','2017-05-02');
