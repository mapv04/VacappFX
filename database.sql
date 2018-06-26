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