DROP DATABASE IF EXISTS gamified_marketing;

CREATE DATABASE IF NOT EXISTS gamified_marketing;

USE gamified_marketing;


-- ------------------------------------------------------------------------ --
-- Usertable -- 
CREATE TABLE Usertable(
	ID int PRIMARY KEY auto_increment,
	username varchar(25) NOT NULL,
	passwd varchar(50) NOT NULL,
	email varchar(50) DEFAULT NULL,
	last_login varchar(10) DEFAULT NULL
);

INSERT INTO Usertable (ID, username, passwd, email)
VALUES (0001, 'user1', 'user1', 'user1@gmail.com');

INSERT INTO Usertable (ID, username, passwd, email)
VALUES (0002, 'user2', 'user2', 'user2@gmail.com');


-- ------------------------------------------------------------------------ --
-- Product -- 
CREATE TABLE Product(
	ID_product int PRIMARY KEY auto_increment,
	p_name varchar(75) NOT NULL,
	image BLOB
);

INSERT INTO Product (ID_product, p_name, image)
VALUES (1010, 'CLup', null);


-- ------------------------------------------------------------------------ --
-- Questionnaire -- 
CREATE TABLE Questionnaire(
	ID_questionnaire int PRIMARY KEY auto_increment,
    q_date varchar(10) NOT NULL,
    product_idx int,
    FOREIGN KEY (product_idx) REFERENCES Product (ID_product) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Questionnaire (ID_questionnaire, q_date, product_idx)
VALUES (1111, '2021-01-06', 1010);


-- ------------------------------------------------------------------------ --
-- Submit -- 
CREATE TABLE Submit(
	user_idx int,
    questionnaire_idx int,
    PRIMARY KEY (user_idx, questionnaire_idx),
    FOREIGN KEY (user_idx) REFERENCES Usertable (ID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (questionnaire_idx) REFERENCES Questionnaire (ID_questionnaire) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Submit (user_idx, questionnaire_idx)
VALUES (0001, 1111);


-- ------------------------------------------------------------------------ --
-- Question -- 
CREATE TABLE Question(
	ID_question int PRIMARY KEY auto_increment,
    question varchar(200) NOT NULL,
    q_type varchar(11) CHECK (q_type IN ('Statistical', 'Marketing')) NOT NULL,
    questionnaire_idx int,
    FOREIGN KEY (questionnaire_idx) REFERENCES Questionnaire (ID_questionnaire) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Question (ID_question, question, q_type, questionnaire_idx)
VALUES (0001, 'Marketing Question 1', 'Marketing', 1111);

INSERT INTO Question (ID_question, question, q_type, questionnaire_idx)
VALUES (0002, 'Marketing Question 2', 'Marketing', 1111);

INSERT INTO Question (ID_question, question, q_type, questionnaire_idx)
VALUES (0003, 'Marketing Question 3', 'Marketing', 1111);

INSERT INTO Question (ID_question, question, q_type, questionnaire_idx)
VALUES (0004, 'Statistical Question 1', 'Statistical', 1111);

INSERT INTO Question (ID_question, question, q_type, questionnaire_idx)
VALUES (0005, 'Statistical Question 2', 'Statistical', 1111);

INSERT INTO Question (ID_question, question, q_type, questionnaire_idx)
VALUES (0006, 'Statistical Question 3', 'Statistical', 1111);


-- ------------------------------------------------------------------------ --
-- Answer -- 
CREATE TABLE Answer(
	ID_answer int PRIMARY KEY auto_increment,
	answer varchar(200),
	user_idx int,
    question_idx int,
    FOREIGN KEY (user_idx) REFERENCES Usertable(ID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (question_idx) REFERENCES Question(ID_question) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Answer (ID_answer, answer, user_idx, question_idx)
VALUES (0001, 'Answer 1', 0001, 0001);

INSERT INTO Answer (ID_answer, answer, user_idx, question_idx)
VALUES (0002, 'Answer 2', 0001, 0002);

INSERT INTO Answer (ID_answer, answer, user_idx, question_idx)
VALUES (0003, 'Answer 3', 0001, 0003);

INSERT INTO Answer (ID_answer, answer, user_idx, question_idx)
VALUES (0004, 'Answer 4', 0001, 0004);

INSERT INTO Answer (ID_answer, answer, user_idx, question_idx)
VALUES (0005, 'Answer 5', 0001, 0005);

INSERT INTO Answer (ID_answer, answer, user_idx, question_idx)
VALUES (0006, 'Answer 6', 0001, 0006);


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