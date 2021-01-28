package it.polimi.db2.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import it.polimi.db2.entities.Answer;
import it.polimi.db2.entities.BadWord;
import it.polimi.db2.entities.Question;
import it.polimi.db2.entities.User;
import it.polimi.db2.exceptions.BadWordException;
import it.polimi.db2.exceptions.QuestionException;


@Stateful
public class AnswerService {
	@PersistenceContext(unitName = "GamifiedMarketingApplication", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	private List<Question> questions = new ArrayList<>();
	
	
	public AnswerService() {}
	
	public void reportAnswers(String[] answers, User user_idx) throws QuestionException, BadWordException {
		for (int i=0; i<answers.length; i++) {
			Answer answ = new Answer(answers[i], user_idx, questions.get(i));
		
		    if(questions.get(i).getQuestion().equals("Expertise level")) {
				if(!(answers[i].toLowerCase().equals("low") || answers[i].toLowerCase().equals("medium") || answers[i].toLowerCase().equals("high") || answers[i].toLowerCase().equals(""))) {
					throw new QuestionException("Malformed answer: the expertise level has to be 'Low', 'Medium' or 'High'");
				}
			}
			
			if(questions.get(i).getQuestion().equals("Sex")) {
				if(!(answers[i].toLowerCase().equals("male") || answers[i].toLowerCase().equals("female") || answers[i].toLowerCase().equals(""))) {
					throw new QuestionException("Malformed answer: the sex has to be 'Male' ore 'Female'");
				}
			}
			
			if(questions.get(i).getQuestion().equals("Age")) {
				if (!(answers[i].toLowerCase().equals(""))) {
				    for(char c : answers[i].toCharArray()){
				        if(!(Character.isDigit(c))){
				        	throw new QuestionException("Malformed answer: the age has to be a number");
				        } 
				    }
				}
			}
			
			if(questions.get(i).getType().equals("Marketing")) {
				if(answers[i].toLowerCase().equals("")) {
					throw new QuestionException("Malformed answer: marketing questions have to be replied");
				}
			}
			
			List<BadWord> badWords = new ArrayList<>();
			badWords = em.createNamedQuery("BadWord.getAllWords", BadWord.class).getResultList();
			
			for (BadWord bad : badWords) {
				if (answers[i].toLowerCase().contains(bad.getWord().toLowerCase())){
					throw new BadWordException("You can't use bad words in your answers");
				}
			}
			
			em.merge(answ);
		}
	}
	
	public void setQuestions(List<Question> qs) {
		this.questions = qs;
	}
	
	@Remove
	public void remove() {}
}
