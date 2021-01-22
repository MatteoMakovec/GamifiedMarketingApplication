package it.polimi.db2.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.Question;
import it.polimi.db2.entities.Questionnaire;

@Stateless
public class QuestionService {
	@PersistenceContext(unitName = "GamifiedMarketingApplication")
	private EntityManager em;

	public QuestionService() {
	}
	
	public List<Question> findQuestions(int ID_questionnaire) {
		List<Question> questions = null;
		
		try {
			questions = em.createNamedQuery("Question.findQuestions", Question.class).setParameter("ID", ID_questionnaire).getResultList();
		} catch (PersistenceException e) {
			throw new PersistenceException("Could not find the questions for this questionnaire");
		}
		
		if (questions.isEmpty())
			return null;
		else 
			return questions;
	}
	
	public void submitQuestion(String quest, Questionnaire questionnaire) {
		Question question = new Question(quest, questionnaire);
		
		em.persist(question);
	}
}
