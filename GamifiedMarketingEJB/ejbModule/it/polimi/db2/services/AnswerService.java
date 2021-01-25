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
import it.polimi.db2.exceptions.BadWordException;
import it.polimi.db2.exceptions.QuestionException;


@Stateless
public class AnswerService {
	@PersistenceContext(unitName = "GamifiedMarketingApplication")
	private EntityManager em;
	
	public AnswerService() {}
	
	public void reportAnswer(String answer, User user_idx, Question question) throws QuestionException, BadWordException {
		Answer answ = new Answer(answer, user_idx, question);
		
	    if(question.getQuestion().equals("Expertise level")) {
			if(!(answer.toLowerCase().equals("low") || answer.toLowerCase().equals("medium") || answer.toLowerCase().equals("high") || answer.toLowerCase().equals(""))) {
				throw new QuestionException("Malformed answer: the expertise level has to be 'Low', 'Medium' or 'High'");
			}
		}
		
		if(question.getQuestion().equals("Sex")) {
			if(!(answer.toLowerCase().equals("male") || answer.toLowerCase().equals("female") || answer.toLowerCase().equals(""))) {
				throw new QuestionException("Malformed answer: the sex has to be 'Male' ore 'Female'");
			}
		}
		
		if(question.getQuestion().equals("Age")) {
			if (!(answer.toLowerCase().equals(""))) {
			    for(char c : answer.toCharArray()){
			        if(!(Character.isDigit(c))){
			        	throw new QuestionException("Malformed answer: the age has to be a number");
			        } 
			    }
			}
		}
		
		if(question.getType().equals("Marketing")) {
			if(answer.toLowerCase().equals("")) {
				throw new QuestionException("Malformed answer: marketing questions have to be replied");
			}
		}
		
		List<BadWord> badWords = new ArrayList<>();
		badWords = em.createNamedQuery("BadWord.getAllWords", BadWord.class).getResultList();
		
		for (BadWord bad : badWords) {
			if (answer.toLowerCase().contains(bad.getWord().toLowerCase())){
				throw new BadWordException("You can't use bad words in your answers");
			}
		}
		
		em.merge(answ);
	}
}
