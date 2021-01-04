package it.polimi.db2.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "Usertable", schema = "gamified_marketing")
@NamedQuery(name = "User.checkCredentials", query = "SELECT r FROM User r  WHERE r.username = :username and r.passwd = :password")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String username;

	private String passwd;
	private String email;
	private String last_login;
	
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "submitters", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH })
	private List<Questionnaire> questionnaires;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "answerer", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH })
	private List<Answer> answers;
	

	public User() {
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.passwd;
	}

	public void setPassword(String password) {
		this.passwd = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLastLogin() {
		return this.last_login;
	}

	public void setLastLogin(String lastLogin) {
		this.last_login = lastLogin;
	}
	

	public List<Questionnaire> getQuestionnaires() {
		return this.questionnaires;
	}

	public void addQuestionnaires(Questionnaire questionnaire) {
		getQuestionnaires().add(questionnaire);
		questionnaire.add(this);
	}

	public void removeQuestionnaires(Questionnaire questionnaire) {
		getQuestionnaires().remove(questionnaire);
	}

	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void addAnswer(Answer answer) {
		getAnswers().add(answer);
		answer.setAnswerer(this);
	}

	public void removeAnswer(Answer answer) {
		getAnswers().remove(answer);
	}
}