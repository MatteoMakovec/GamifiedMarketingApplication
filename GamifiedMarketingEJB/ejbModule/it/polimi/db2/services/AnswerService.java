package it.polimi.db2.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.entities.Answer;
import it.polimi.db2.entities.Question;
import it.polimi.db2.entities.User;


@Stateless
public class AnswerService {
	@PersistenceContext(unitName = "GamifiedMarketingApplication")
	private EntityManager em;
	
	public AnswerService() {}
	
	public void reportAnswers(String[] answers, User user_idx, List<Question> questions) {
		Answer answer = new Answer(answers[0], user_idx, questions.get(0));
		
		em.persist(answer);
		
		/*
		for (int i=0; i<answers.length; i++) {
			Answer answer = new Answer(answers[i], user_idx, questions.get(i));
			
			em.persist(answer);
		}*/
	}
	
	public void reportAnswerS(String[] answers, User user_idx, List<Question> questions) {
		for (int i=0; i<answers.length; i++) {
			Answer answer = new Answer();
			
			em.persist(answer);
			answer.setAnswer(answers[i]);
			answer.setQuestion(questions.get(i));
			answer.setUser(user_idx);
		}
	}
}
