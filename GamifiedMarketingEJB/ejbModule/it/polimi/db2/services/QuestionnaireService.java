package it.polimi.db2.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Questionnaire;

@Stateless
public class QuestionnaireService {
	@PersistenceContext(unitName = "GamifiedMarketingApplication")
	private EntityManager em;

	public QuestionnaireService() {
	}

	public Questionnaire findDailyQuestionnaire(String date) {
		Questionnaire questionnaire = null;
		
		try {
			questionnaire = em.createNamedQuery("Questionnaire.findDailyQuestionnaire", Questionnaire.class).setParameter("date", date)
					.getSingleResult();
		} catch (PersistenceException e) {
			throw new PersistenceException("Could not find the daily questionnaire");
		}
		
		if (questionnaire == null)
			return null;
		else 
			return questionnaire;
	}
	
	public void createQuestionnaire(String q_date, Product product){
		Questionnaire questionnaire = new Questionnaire(q_date, product);
		
		em.persist(questionnaire);
	}
}
