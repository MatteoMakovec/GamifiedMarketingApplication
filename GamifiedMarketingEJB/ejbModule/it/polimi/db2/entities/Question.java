package it.polimi.db2.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Question", schema = "gamified_marketing")
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int ID_question;
	
	private String question;
	private String q_type;
	private int questionnaire_idx;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "answerTo", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH })
	private List<Answer> answers;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH })
	private Questionnaire questionnaire;

	public Question() {
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
	
	public int getQuestionnaire() {
		return this.questionnaire_idx;
	}
	
	public void setQuestionnaire(int questionnaire) {
		this.questionnaire_idx = questionnaire;
	}
	
	public List<Answer> getAnswer() {
		return this.answers;
	}

	public void add(Answer answer) {
		getAnswer().add(answer);
		answer.setAnswerTo(this);
	}

	public void removeAnswer(Answer answer) {
		getAnswer().remove(answer);
	}
	
	public void setQuestionnaire(Questionnaire questionnaireSet) {
		questionnaire_idx = questionnaireSet.getID();
		questionnaire = questionnaireSet;
	}
}