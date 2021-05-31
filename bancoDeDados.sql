DROP DATABASE Agenda;

/*Cria o banco com todos os tratamentos*/
CREATE DATABASE Agenda 
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_general_ci; 

/*Seleciona o banco*/
USE Agenda;

/*Tabela de usuários, que será usada no login*/
CREATE TABLE usuario (
    id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    data_nasc DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    login VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    PRIMARY KEY (id,login)
)  DEFAULT CHARSET=UTF8 ENGINE=INNODB;

/*Tabela das importâncias, o usuário não fará alteração*/
CREATE TABLE importancia (
	id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY
)  DEFAULT CHARSET=UTF8 ENGINE=INNODB;

/*Tabela da frequencia do evento, o usuário não fará alteração*/
CREATE TABLE frequencia (
	id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY
)  DEFAULT CHARSET=UTF8 ENGINE=INNODB;

/*Tabela das categorias de eventos, o usuário não fará alteração*/
CREATE TABLE categoria (
	id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    corR TINYINT UNSIGNED,
    corG TINYINT UNSIGNED,
    corB TINYINT UNSIGNED
)  DEFAULT CHARSET=UTF8 ENGINE=INNODB;

/*Tabela principal, a qual o usuário poderá adicionar, remover, e modificar dados*/
CREATE TABLE evento (
	id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_evento DATE NOT NULL,
    hora_inicio TIME,
    hora_fim TIME,
    status_ativo ENUM('A','I') DEFAULT 'A',
    id_usuario TINYINT UNSIGNED NOT NULL,
    id_importancia TINYINT UNSIGNED NOT NULL,
    id_frequencia TINYINT UNSIGNED NOT NULL,
    id_categoria TINYINT UNSIGNED NOT NULL,
	FOREIGN KEY (id_usuario)
		REFERENCES usuario (id),
	FOREIGN KEY (id_importancia)
		REFERENCES importancia (id),
	FOREIGN KEY (id_frequencia)
		REFERENCES frequencia (id),
    FOREIGN KEY (id_categoria)
		REFERENCES categoria (id)
)  DEFAULT CHARSET=UTF8 ENGINE=INNODB;

INSERT INTO importancia VALUES(default),
							  (default),
							  (default),
							  (default),
							  (default);

INSERT INTO frequencia VALUES(default),
							 (default),
							 (default),
							 (default),
							 (default);

INSERT INTO categoria VALUES(default,0,178,100),
							(default,0,160,118),
							(default,0,128,140);
                            
INSERT INTO evento VALUES(default,'caminhada','2018-12-01','08:00','08:59',default, 1, 3, 2, 3),
						 (default,'cuidar do jardim','2018-12-01','09:00','09:59',default, 1, 4, 2, 3),
                         (default,'aniversário Pedro','2018-12-01','07:00','10:59',default, 1, 5, 5, 1),
                         (default,'reunião','2018-12-02','10:00','11:59',default, 1, 5, 4, 3),
                         (default,'encontro com amigos','2018-12-02','18:00','18:59',default, 1, 4, 1, 3),
                         (default,'rodízio de pizza','2018-12-04','18:00','20:59',default, 1, 3, 1, 3),
                         (default,'andar de bicicleta','2018-12-05','18:00','19:59',default, 1, 2, 1, 3),
                         (default,'assistir séries','2018-12-07','18:00','21:59',default, 1, 1, 1, 3),
                         (default,'corrida','2018-12-09','18:00','18:39',default, 1, 2, 1, 3),
                         (default,'olhar o email','2018-12-09','18:00','18:29',default, 1, 3, 1, 3),
                         (default,'aniversário Marcos','2018-12-11','18:00','20:59',default, 1, 4, 5, 3),
                         (default,'encontro','2018-12-12','18:00','19:59',default, 1, 5, 1, 3),
                         (default,'estudar física','2018-12-13','20:00','21:59',default, 1, 1, 1, 3),
                         (default,'levar o cachorro num passeio','2018-12-14','10:00','15:59',default, 1, 2, 1, 3),
                         (default,'levar o cachorro num petshop','2018-12-15','08:00','08:59',default, 1, 4, 1, 3),
                         (default,'sair com a familia','2018-12-12','08:00','18:59',default, 1, 5, 1, 3),
                         (default,'ir para o spa','2018-12-17','08:00','11:59',default, 1, 3, 1, 3),
                         (default,'depilação a laser','2018-12-22','15:00','17:59',default, 1, 1, 1, 3),
                         (default,'churrasco','2018-12-21','11:00','16:59',default, 1, 2, 1, 3),
                         (default,'aniversário Antônio','2018-12-09','18:00','21:59',default, 1, 3, 5, 3),
                         (default,'viajar','2018-12-06','08:01','08:02',default, 1, 3, 1, 3),
                         (default,'rally','2018-12-12','08:00','12:59',default, 1, 3, 1, 3),
                         (default,'jantar dancante','2018-12-20','18:00','19:59',default, 1, 1, 1, 3),
                         (default,'reunião de pais','2018-12-18','19:00','20:59',default, 1, 3, 1, 3),
                         (default,'aula particular','2018-12-15','18:00','19:59',default, 1, 3, 1, 3),
                         (default,'reunião na empresa','2018-12-16','08:00','10:59',default, 1, 2, 1, 3),
                         (default,'encontro com amigos','2018-12-27','18:00','21:59',default, 1, 4, 1, 3),
                         (default,'aniversário Luis','2018-12-31','08:00','13:59',default, 1, 3, 5, 3),
                         (default,'almoço de família','2018-12-31','12:00','14:59',default, 1, 3, 1, 3),
                         (default,'caminhada','2018-12-31','08:00','08:59',default, 1, 1, 1, 3),
                         (default,'aniversário Clara','2018-12-31','10:00','18:59',default, 1, 3, 5, 3),
                         (default,'pesquisa trabalho','2018-12-31','20:00','22:59',default, 1, 5, 1, 3);

INSERT INTO usuario VALUES(default,'JOÃO ARTHUR LOTHAMER FERNANDES','2000-08-22','JOAO.LOTHAMER@GMAIL.COM','JOAOADMIN','SENHABOA');
