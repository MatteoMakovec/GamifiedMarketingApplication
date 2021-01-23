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
	last_login varchar(20) DEFAULT NULL,
    user_type varchar(6) DEFAULT 'Normal' CHECK (user_type IN ('Normal', 'Admin'))
);

INSERT INTO Usertable (ID, username, passwd, email)
VALUES (0001, 'user1', 'user1', 'user1@gmail.com');

INSERT INTO Usertable (ID, username, passwd, email)
VALUES (0002, 'user2', 'user2', 'user2@gmail.com');

INSERT INTO Usertable (ID, username, passwd, email, user_type)
VALUES (0003, 'user3', 'user3', 'user3@gmail.com', 'Admin');


-- ------------------------------------------------------------------------ --
-- Product -- 
CREATE TABLE Product(
	ID_product int PRIMARY KEY auto_increment,
	p_name varchar(75) NOT NULL,
	image MEDIUMBLOB
);


-- ------------------------------------------------------------------------ --
-- Questionnaire -- 
CREATE TABLE Questionnaire(
	ID_questionnaire int PRIMARY KEY auto_increment,
    q_date varchar(10) NOT NULL,
    product_idx int,
    FOREIGN KEY (product_idx) REFERENCES Product (ID_product)
);


-- ------------------------------------------------------------------------ --
-- Question -- 
CREATE TABLE Question(
	ID_question int PRIMARY KEY auto_increment,
    question varchar(200) NOT NULL,
    q_type varchar(11) CHECK (q_type IN ('Statistical', 'Marketing')) NOT NULL,
    questionnaire_idx int,
    FOREIGN KEY (questionnaire_idx) REFERENCES Questionnaire (ID_questionnaire) ON UPDATE CASCADE
);


-- ------------------------------------------------------------------------ --
-- Answer -- 
CREATE TABLE Answer(
	ID_answer int PRIMARY KEY auto_increment,
	answer varchar(250) NOT NULL,
	user_idx int NOT NULL,
    question_idx int NOT NULL,
    FOREIGN KEY (user_idx) REFERENCES Usertable (ID),
    FOREIGN KEY (question_idx) REFERENCES Question (ID_question)
);


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


-- ------------------------------------------------------------------------ --
-- Leaderboard -- 
CREATE TABLE Leaderboard(
	ID int,
	questionnaire int,
    points int,
    PRIMARY KEY (ID, questionnaire),
    FOREIGN KEY (ID) REFERENCES Usertable (ID),
    FOREIGN KEY (questionnaire) REFERENCES Questionnaire (ID_questionnaire)
);


-- ------------------------------------------------------------------------ --
-- ------------------ TRIGGERS ------------------ -- 
DELIMITER $$

CREATE TRIGGER CopyStatisticalQuestion
AFTER INSERT ON `Questionnaire`
FOR EACH ROW
BEGIN
    INSERT INTO Question (question, q_type, questionnaire_idx) VALUES ('Age', 'Statistical', new.ID_questionnaire);
    INSERT INTO Question (question, q_type, questionnaire_idx) VALUES ('Sex', 'Statistical', new.ID_questionnaire);
	INSERT INTO Question (question, q_type, questionnaire_idx) VALUES ('Expertise level', 'Statistical', new.ID_questionnaire);
END$$

DELIMITER ;
