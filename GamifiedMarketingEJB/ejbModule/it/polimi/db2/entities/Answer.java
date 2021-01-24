package it.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "Answer", schema = "gamified_marketing")
public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_answer")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID_answer;
	
	@Column(name="answer")
	private String answ;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "user_idx")
	private User user_idx;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "question_idx")
	private Question question_idx;
	
	
	public Answer() {}
	
	public Answer(String answ, User usr, Question q) {
		this.answ = answ;
		this.user_idx = usr;
		this.question_idx = q;
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
		return this.answ;
	}

	public void setAnswer(String answer) {
		this.answ = answer;
	}
}