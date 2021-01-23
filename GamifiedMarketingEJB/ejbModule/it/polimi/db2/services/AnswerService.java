package it.polimi.db2.services;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.entities.Answer;
import it.polimi.db2.entities.BadWord;
import it.polimi.db2.entities.Question;
import it.polimi.db2.entities.User;


@Stateless
public class AnswerService {
	@PersistenceContext(unitName = "GamifiedMarketingApplication")
	private EntityManager em;
	
	public AnswerService() {}
	
	public void reportAnswer(String answer, User user_idx, Question question) {
		Answer answ = new Answer(answer, user_idx, question);
		
		if(question.getQuestion().equals("Expertise level")) {
			if((!answer.toLowerCase().equals("low") || answer.toLowerCase().equals("medium") || answer.toLowerCase().equals("high"))) {
				answ.setAnswer("");
			}
		}
		
		// Questo controllo non funziona
		boolean numeric = true;
		if(question.getQuestion().equals("Age")) {
			try {
	            int num = Integer.parseInt(answer);
	        } catch (NumberFormatException e) {
	            numeric = false;
	        }
			
			if (numeric = false) {
				answ.setAnswer("");
			}
		}

		
		List<BadWord> badWords = new ArrayList<>();
		badWords = em.createNamedQuery("BadWord.getAllWords", BadWord.class).getResultList();
		
		for (BadWord bad : badWords) {
			if (answer.toLowerCase().contains(bad.getWord().toLowerCase())){
				answ.setAnswer("");
			}
		}
		
		em.persist(answ);
	}
}
