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
	private List<Answer> answers = new ArrayList<>();
	
	
	public AnswerService() {}
	
	public void reportAnswers(User user_idx) throws QuestionException, BadWordException {
		for (int i=0; i<answers.size(); i++) {
		    if(questions.get(i).getQuestion().equals("Expertise level")) {
				if(!(answers.get(i).getAnswer().toLowerCase().equals("low") || answers.get(i).getAnswer().toLowerCase().equals("medium") || answers.get(i).getAnswer().toLowerCase().equals("high") || answers.get(i).getAnswer().toLowerCase().equals(""))) {
					throw new QuestionException("Malformed answer: the expertise level has to be 'Low', 'Medium' or 'High'");
				}
			}
			
			if(questions.get(i).getQuestion().equals("Sex")) {
				if(!(answers.get(i).getAnswer().toLowerCase().equals("male") || answers.get(i).getAnswer().toLowerCase().equals("female") || answers.get(i).getAnswer().toLowerCase().equals(""))) {
					throw new QuestionException("Malformed answer: the sex has to be 'Male' ore 'Female'");
				}
			}
			
			if(questions.get(i).getQuestion().equals("Age")) {
				if (!(answers.get(i).getAnswer().toLowerCase().equals(""))) {
				    for(char c : answers.get(i).getAnswer().toCharArray()){
				        if(!(Character.isDigit(c))){
				        	throw new QuestionException("Malformed answer: the age has to be a number");
				        } 
				    }
				}
			}
			
			if(questions.get(i).getType().equals("Marketing")) {
				if(answers.get(i).getAnswer().toLowerCase().equals("")) {
					throw new QuestionException("Malformed answer: marketing questions have to be replied");
				}
			}
			
			List<BadWord> badWords = new ArrayList<>();
			badWords = em.createNamedQuery("BadWord.getAllWords", BadWord.class).getResultList();
			
			for (BadWord bad : badWords) {
				if (answers.get(i).getAnswer().toLowerCase().contains(bad.getWord().toLowerCase())){
					throw new BadWordException("You can't use bad words in your answers");
				}
			}
			
			em.merge(answers.get(i));
		}
	}
	
	public void setQuestions(List<Question> qs) {
		for (Question q : qs) {
			questions.add(q);
		}
	}
	
	public void addAnswers(String[] answs, User user) {
		for (int i=0; i<answs.length; i++) {
			Answer answ = new Answer(answs[i], user, questions.get(answers.size()));
			answers.add(answ);
		}
	}
	
	@Remove
	public void remove() {}
}
