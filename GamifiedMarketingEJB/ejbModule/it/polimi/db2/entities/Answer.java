package it.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "Answer", schema = "gamified_marketing")
@IdClass(AnswerId.class)
public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String username_idx;
	
	@Id
	private int question_idx;

	private String answer;
	
	
	@ManyToOne
	@JoinColumn(name = "answerer")
	private User answerer;
	
	@ManyToOne
	@JoinColumn(name = "answerTo")
	private Question answerTo;
	
	
	public Answer() {
	}

	public String getUsername() {
		return this.username_idx;
	}

	public void setUsername(String username) {
		this.username_idx = username;
	}

	public int getQuestion() {
		return this.question_idx;
	}

	public void setQuestion(int ID_question) {
		this.question_idx = ID_question;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public void setAnswerer(User answerer) {
		username_idx = answerer.getUsername();
		this.answerer = answerer;
	}
	
	public void setAnswerTo(Question answerTo) {
		question_idx = answerTo.getID();
		this.answerTo = answerTo;
	}
}