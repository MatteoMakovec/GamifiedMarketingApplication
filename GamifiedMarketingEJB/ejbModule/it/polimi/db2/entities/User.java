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
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
	
	private String username;
	private String passwd;
	private String email;
	private String last_login;
	private String user_type;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user_idx", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH })
	private List<Answer> answers;
	

	public User() {}
	
	public User(String username, String password, String email) {
		this.username = username;
		this.passwd = password;
		this.email = email;
		user_type = "Normal";
	}
	
	public int getID() {
		return this.ID;
	}

	public void setID(int ID) {
		this.ID = ID;
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
	
	public String getType() {
		return this.user_type;
	}

	public void setType(String user_type) {
		this.user_type = user_type;
	}
	
	public String getLastLogin() {
		return this.last_login;
	}

	public void setLastLogin(String lastLogin) {
		this.last_login = lastLogin;
	}
	
	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void addAnswer(Answer answer) {
		getAnswers().add(answer);
		answer.setUser(this);
	}

	public void removeAnswer(Answer answer) {
		getAnswers().remove(answer);
	}
}