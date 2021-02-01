package it.polimi.db2.services;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Question;
import it.polimi.db2.entities.Questionnaire;


@Stateful
public class QuestionnaireCreationService {
	@PersistenceContext(unitName = "GamifiedMarketingApplication", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	private Product product;
	private String questionnaireDate;
	
	public QuestionnaireCreationService() {}
	
	public void createQuestionnaire(String[] questions) {
		Questionnaire questionnaire = new Questionnaire(questionnaireDate);
		em.persist(questionnaire);
		
		questionnaire.setProduct(product);
		
		for (int i=0; i<questions.length; i++) {
			Question q = new Question(questions[i], questionnaire);
			questionnaire.add(q);
		}
	}
	
	public void addProduct(String productName, byte[] image) {
		this.product = new Product (productName, image);
	}
	
	public void addQuestionnaireDate(String date) {
		this.questionnaireDate = date;
	}
	
	@Remove
	public void remove() {}
}
