package it.polimi.db2.entities;

import java.io.Serializable;

public class AnswerId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username_idx;
	private int question_idx;
	
	
	public AnswerId() {}
	
	public AnswerId(String username, int ID_question) {
		this.username_idx = username;
		this.question_idx = ID_question;
	}
	
	public boolean equals(Object other) {
        if (this == other)
            return true;
        
        if (!(other instanceof AnswerId))
            return false;
        AnswerId castOther = (AnswerId) other;
        return username_idx == castOther.username_idx && question_idx == castOther.question_idx;
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + Integer.parseInt(username_idx);
        hash = hash * prime + this.question_idx;
        return hash;
    }
}
