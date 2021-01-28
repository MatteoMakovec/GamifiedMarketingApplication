package it.polimi.db2.entities;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name = "Leaderboard", schema = "gamified_marketing")
@NamedQueries({
	@NamedQuery(name = "Leaderboard.findLeaderboard", query = "SELECT l FROM Leaderboard l  WHERE l.questionnaire_ID = :questionnaire ORDER BY l.points DESC"),
	@NamedQuery(name = "Leaderboard.findCancel", query = "SELECT l FROM Leaderboard l  WHERE l.questionnaire_ID = :questionnaire AND l.points = 0"),
})
public class Leaderboard implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID_leaderboard")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID_leaderboard;
	
	private int points;
	private int user_ID;
	private int questionnaire_ID;
	

	public Leaderboard() {}
	
	public Leaderboard(int user, int questionnaire) {
		user_ID = user;
		questionnaire_ID = questionnaire;
		points = 0;
	}
	
	public int getID() {
		return this.ID_leaderboard;
	}

	public void setID(int ID_leaderboard) {
		this.ID_leaderboard = ID_leaderboard;
	}
	
	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getQuestionnaires() {
		return this.questionnaire_ID;
	}

	public void setQuestionnaire(int questionnaire) {
		this.questionnaire_ID = questionnaire;
	}

	public int getUser() {
		return this.user_ID;
	}

	public void setUser(int user) {
		this.user_ID = user;
	}
}