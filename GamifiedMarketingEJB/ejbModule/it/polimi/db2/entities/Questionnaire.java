package it.polimi.db2.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "Questionnaire", schema = "gamified_marketing")
@NamedQuery(name = "Questionnaire.findDailyQuestionnaire", query = "SELECT q FROM Questionnaire q  WHERE q.q_date = :date")
public class Questionnaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int ID_questionnaire;
	
	private String q_date;
	

	@OneToOne
	private Product product;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH })
	private List<User> submitters;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH })
	@JoinColumn(name="questions")
	private List<Question> questions;


	public Questionnaire() {
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
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}

	public List<User> getSubmitters() {
		return this.submitters;
	}

	public void add(User submitter) {
		getSubmitters().add(submitter);
	}

	public void removeSubmitter(User submitter) {
		getSubmitters().remove(submitter);
	}

	public List<Question> getQuestion() {
		return this.questions;
	}

	public void add(Question question) {
		getQuestion().add(question);
		question.setQuestionnaire(this);
	}

	public void removeQuestion(Question question) {
		getQuestion().remove(question);
	}
}