package it.polimi.db2.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.NonUniqueResultException;

import it.polimi.db2.entities.Answer;
import it.polimi.db2.entities.Questionnaire;
import it.polimi.db2.entities.User;
import it.polimi.db2.exceptions.*;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserService {
	@PersistenceContext(unitName = "GamifiedMarketingApplication")
	private EntityManager em;

	public UserService() {
	}

	public User checkCredentials(String usr, String pwd) throws CredentialsException, NonUniqueResultException {
		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.checkCredentials", User.class).setParameter("username", usr).setParameter("password", pwd)
					.getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1)
			return uList.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}
	
	public User createUser(String username, String password, String email) {
		User user = new User(username, password, email);
		
		em.persist(user);
		
		return user;
	}
	
	public void updateUser(User u) throws Exception {
		try {
			em.merge(u);
		} catch (Exception e) {
			throw new Exception("Could not update timestamp");
		}
	}
	
	public List<Answer> getAnswers(User user, int questionnaire) {
		List<Answer> answerList = new ArrayList<>();
		Questionnaire quest = em.find(Questionnaire.class, questionnaire);
		try {
			answerList = em.createNamedQuery("User.getAnswers", Answer.class).setParameter("user", user).setParameter("questionnaire", quest)
					.getResultList();
		} catch (PersistenceException e) {
			throw new PersistenceException("Could not get the user answers");
		}
		
		return answerList;
	}
}
