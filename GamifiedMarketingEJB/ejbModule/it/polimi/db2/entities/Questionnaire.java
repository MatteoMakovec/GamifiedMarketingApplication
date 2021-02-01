package it.polimi.db2.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "Questionnaire", schema = "gamified_marketing")
@NamedQueries({
	@NamedQuery(name = "Questionnaire.findDailyQuestionnaire", query = "SELECT q FROM Questionnaire q  WHERE q.q_date = :date"),
	@NamedQuery(name = "Questionnaire.findQuestionnaireDP", query = "SELECT q FROM Questionnaire q  WHERE q.q_date = :date AND q.product_idx = :product"),
	@NamedQuery(name = "Questionnaire.findAllQuestionnaires", query = "SELECT q FROM Questionnaire q")
})


public class Questionnaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_questionnaire")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID_questionnaire;
	
	private String q_date;
	
	
	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
	@JoinColumn(name="product_idx")
	private Product product_idx;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "questionnaire_idx", cascade = { CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private List<Question> questions = new ArrayList<>();
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "questionnaire_ID", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH }, orphanRemoval = true)
	private List<Leaderboard> leaderboards = new ArrayList<>();*/


	public Questionnaire() {
	}
	
	public Questionnaire(String date, Product product) {
		q_date = date;
		product_idx = product;
	}
	
	public int getID() {
		return this.ID_questionnaire;
	}

	public void setID(int ID_questionnaire) {
		this.ID_questionnaire = ID_questionnaire;
	}
	
	public String getDate() {
		return this.q_date;
	}

	public void setDate(String date) {
		this.q_date = date;
	}
	
	public Product getProduct() {
		return product_idx;
	}
	
	public void setProduct(Product product) {
		this.product_idx = product;
	}
	
	public List<Question> getQuestions() {
		return this.questions;
	}

	public void add(Question question) {
		questions.add(question);
		question.setQuestionnaire(this);
	}

	public void removeQuestion(Question question) {
		getQuestions().remove(question);
	}
	/*
	public List<Leaderboard> getLeaderboards() {
		return this.leaderboards;
	}

	public void addLeaderboard(Leaderboard leaderboard) {
		getLeaderboards().add(leaderboard);
		leaderboard.setQuestionnaire(this);
	}

	public void removeLeaderboard(Leaderboard leaderboard) {
		getLeaderboards().remove(leaderboard);
	}*/
}