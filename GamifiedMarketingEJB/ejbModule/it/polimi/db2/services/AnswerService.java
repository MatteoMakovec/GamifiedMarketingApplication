package it.polimi.db2.services;

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
	
	public void reportAnswer(String answ, User user_idx, Question question_idx) {
		Answer answer = new Answer(answ, user_idx, question_idx);
		
		em.persist(answer);
	}
}
