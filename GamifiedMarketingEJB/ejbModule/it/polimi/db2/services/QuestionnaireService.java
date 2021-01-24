package it.polimi.db2.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Question;
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
	
	public void createQuestionnaire(String q_date, int productID, String[] questions){
		Product product = em.find(Product.class, productID);
		Questionnaire questionnaire = new Questionnaire(q_date, product);
		
		em.persist(questionnaire);
		
		for (int i=0; i<questions.length; i++) {
			Question q = new Question(questions[i], questionnaire);
			questionnaire.add(q);
		}
	}
	
	
	public Questionnaire findQuestionnaire (String date, Product product) {
		List<Questionnaire> questionnaires = new ArrayList<>();
		
		questionnaires = em.createNamedQuery("Questionnaire.findQuestionnaireDP", Questionnaire.class).setParameter("date", date).setParameter("product", product)
				.getResultList();
		
		if(questionnaires.isEmpty()) {
			return null;
		}
		else {
			return questionnaires.get(questionnaires.size()-1);
		}
		
	}
	
	public List<Questionnaire> findQuestionnaires () {
		List<Questionnaire> questionnaires = new ArrayList<>();
		
		questionnaires = em.createNamedQuery("Questionnaire.findAllQuestionnaires", Questionnaire.class)
				.getResultList();
		
		if(questionnaires.isEmpty()) {
			return null;
		}
		else {
			return questionnaires;
		}
	}
	
	public void deleteQuestionnaire (int questionnaireID) {
		Questionnaire questionnaire = em.find(Questionnaire.class, questionnaireID);
		
		em.remove(questionnaire);
	}
}
