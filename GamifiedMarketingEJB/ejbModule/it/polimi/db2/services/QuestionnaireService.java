package it.polimi.db2.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
		List<Questionnaire> questionnaires = null;
		
		try {
			questionnaires = em.createNamedQuery("Questionnaire.findDailyQuestionnaire", Questionnaire.class).setParameter("date", date)
					.getResultList();
		} catch (PersistenceException e) {
			throw new PersistenceException("Could not find the daily questionnaire");
		}
		
		if (questionnaires.isEmpty())
			return null;
		else 
			return questionnaires.get(questionnaires.size()-1);
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
		
		Date systemDate = new java.sql.Date(System.currentTimeMillis());
		Date date = Date.valueOf(questionnaire.getDate());
		
		if(date.compareTo(systemDate) < 0) {
			em.remove(questionnaire);
	    }
	}
}
