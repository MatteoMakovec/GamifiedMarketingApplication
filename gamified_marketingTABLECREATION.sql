DROP DATABASE IF EXISTS gamified_marketing;

CREATE DATABASE IF NOT EXISTS gamified_marketing;

USE gamified_marketing;


-- ------------------------------------------------------------------------ --
-- Usertable -- 
CREATE TABLE Usertable(
	username varchar(25) PRIMARY KEY,
	passwd varchar(25) NOT NULL,
	email varchar(50) DEFAULT NULL,
	last_login date DEFAULT NULL
);

INSERT INTO Usertable (username, passwd, email)
VALUES ('user1', 'user1', 'user1@gmail.com');

INSERT INTO Usertable (username, passwd, email)
VALUES ('user2', 'user2', 'user2@gmail.com');


-- ------------------------------------------------------------------------ --
-- Product -- 
CREATE TABLE Product(
	p_name varchar(75) PRIMARY KEY,
	image BLOB
);

INSERT INTO Product (p_name, image)
VALUES ('CLup', null);


-- ------------------------------------------------------------------------ --
-- Questionnaire -- 
CREATE TABLE Questionnaire(
	ID_questionnaire int(50) PRIMARY KEY,
    q_date date NOT NULL,
    name_idx varchar(75),
    FOREIGN KEY (name_idx) REFERENCES Product (p_name) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Questionnaire (ID_questionnaire, q_date, name_idx)
VALUES (1111, '2020-01-04', 'CLup');


-- ------------------------------------------------------------------------ --
-- Submit -- 
CREATE TABLE Submit(
	username_idx varchar(25),
    questionnaire_idx int(50),
    PRIMARY KEY (username_idx, questionnaire_idx),
    FOREIGN KEY (username_idx) REFERENCES Usertable (username) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (questionnaire_idx) REFERENCES Questionnaire (ID_questionnaire) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Submit (username_idx, questionnaire_idx)
VALUES ('user1', 1111);


-- ------------------------------------------------------------------------ --
-- Question -- 
CREATE TABLE Question(
	ID_question int(50) PRIMARY KEY auto_increment,
    question varchar(200) NOT NULL,
    q_type varchar(11) CHECK (q_type IN ('Statistical', 'Marketing')) NOT NULL,
    questionnaire_idx int(50),
    FOREIGN KEY (questionnaire_idx) REFERENCES Questionnaire (ID_questionnaire) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Question (ID_question, question, q_type, questionnaire_idx)
VALUES (1111, 'Marketing Question 1', 'Marketing', 1111);

INSERT INTO Question (ID_question, question, q_type, questionnaire_idx)
VALUES (1112, 'Marketing Question 2', 'Marketing', 1111);

INSERT INTO Question (ID_question, question, q_type, questionnaire_idx)
VALUES (1113, 'Marketing Question 3', 'Marketing', 1111);

INSERT INTO Question (ID_question, question, q_type, questionnaire_idx)
VALUES (1114, 'Statistical Question 1', 'Statistical', 1111);

INSERT INTO Question (ID_question, question, q_type, questionnaire_idx)
VALUES (1115, 'Statistical Question 2', 'Statistical', 1111);

INSERT INTO Question (ID_question, question, q_type, questionnaire_idx)
VALUES (1116, 'Statistical Question 3', 'Statistical', 1111);


-- ------------------------------------------------------------------------ --
-- Answer -- 
CREATE TABLE Answer(
	answer varchar(200),
	username_idx varchar(25),
    question_idx int(50),
    PRIMARY KEY (username_idx, question_idx),
    FOREIGN KEY (username_idx) REFERENCES Usertable(username) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (question_idx) REFERENCES Question(ID_question) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Answer (answer, username_idx, question_idx)
VALUES ('Answer 1', 'user1', 1111);

INSERT INTO Answer (answer, username_idx, question_idx)
VALUES ('Answer 2', 'user1', 1112);

INSERT INTO Answer (answer, username_idx, question_idx)
VALUES ('Answer 3', 'user1', 1113);

INSERT INTO Answer (answer, username_idx, question_idx)
VALUES ('Answer 4', 'user1', 1114);

INSERT INTO Answer (answer, username_idx, question_idx)
VALUES ('Answer 5', 'user1', 1115);

INSERT INTO Answer (answer, username_idx, question_idx)
VALUES ('Answer 6', 'user1', 1116);


-- ------------------------------------------------------------------------ --
-- BadWords -- 
CREATE TABLE BadWord(
	word varchar(25) PRIMARY KEY
);

INSERT INTO BadWord (word)
VALUES ('badword1');

INSERT INTO BadWord (word)
VALUES ('badword2');

INSERT INTO BadWord (word)
VALUES ('badword3');