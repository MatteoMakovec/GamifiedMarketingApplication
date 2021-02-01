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
	
	private Product p;
	
	public QuestionnaireCreationService() {}
	
	public void createQuestionnaire(String q_date, int productID, String[] questions) {
		Product product = em.find(Product.class, productID);
		Questionnaire questionnaire = new Questionnaire(q_date, product);
		
		em.persist(questionnaire);
		
		for (int i=0; i<questions.length; i++) {
			Question q = new Question(questions[i], questionnaire);
			questionnaire.add(q);
		}
	}
	
	public void addProduct(String productName, byte[] image) {
		this.p = new Product (productName, image);
	}
	
	@Remove
	public void remove() {}
}
