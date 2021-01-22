package it.polimi.db2.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Question", schema = "gamified_marketing")
@NamedQuery(name="Question.findQuestions", query="SELECT q FROM Question q WHERE q.questionnaire_idx.ID_questionnaire = :ID")
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_question")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID_question;
	
	private String question;
	private String q_type;
	
	/*
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "question_idx", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH })
	private List<Answer> answers;*/
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH })
	@JoinColumn(name="questionnaire_idx")
	private Questionnaire questionnaire_idx;

	public Question() {}
	
	public Question(String question, Questionnaire questionnaire) {
		this.question = question;
		this.questionnaire_idx = questionnaire;
		q_type = "Marketing";
	}
	
	public int getID() {
		return this.ID_question;
	}

	public void setID(String question) {
		this.question = question;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getType() {
		return this.q_type;
	}
	
	public void setType(String type) {
		this.q_type = type;
	}
	
	public Questionnaire getQuestionnaire() {
		return this.questionnaire_idx;
	}
	
	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire_idx = questionnaire;
	}
	/*
	public List<Answer> getAnswer() {
		return this.answers;
	}

	public void add(Answer answer) {
		getAnswer().add(answer);
		answer.setQuestion(this);
	}

	public void removeAnswer(Answer answer) {
		getAnswer().remove(answer);
	}*/
}