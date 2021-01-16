package it.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "Answer", schema = "gamified_marketing")
public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_answer")
	private int ID_answer;
	
	private String answer;
	
	public Answer (String answer, User user_idx, Question question_idx) {
		this.answer = answer;
		this.user_idx = user_idx;
		this.question_idx = question_idx;
	}
	
	@ManyToOne
	@JoinColumn(name = "user_idx")
	private User user_idx;
	
	@ManyToOne
	@JoinColumn(name = "question_idx")
	private Question question_idx;
	
	
	public Answer() {
	}

	public User getUser() {
		return this.user_idx;
	}

	public void setUser(User user) {
		this.user_idx = user;
	}

	public Question getQuestion() {
		return this.question_idx;
	}

	public void setQuestion(Question question) {
		this.question_idx = question;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}