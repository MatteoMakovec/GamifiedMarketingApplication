package it.polimi.db2.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.Leaderboard;
import it.polimi.db2.entities.Questionnaire;
import it.polimi.db2.entities.User;

@Stateless
public class LeaderboardService {
	@PersistenceContext(unitName = "GamifiedMarketingApplication")
	private EntityManager em;

	public LeaderboardService() {}

	public List<Leaderboard> getLeaderboards(int questionnaireID) {
		List<Leaderboard> leaderboards = new ArrayList<>();
		Questionnaire quest = em.find(Questionnaire.class, questionnaireID);
		
		try {
			leaderboards = em.createNamedQuery("Leaderboard.findLeaderboard", Leaderboard.class).setParameter("questionnaire", quest)
					.setHint("javax.persistence.cache.storeMode", "REFRESH").getResultList();
		} catch (PersistenceException e) {
			throw new PersistenceException("Could not get the leaderboard");
		}
		
		if (leaderboards.isEmpty())
			return null;
		else 
			return leaderboards;
	}
	
	public List<User> getUsers(int questionnaireID) {
		List<Leaderboard> leaderboards = new ArrayList<>();
		List<User> users = new ArrayList<>();
		
		Questionnaire quest = em.find(Questionnaire.class, questionnaireID);
		try {
			leaderboards = em.createNamedQuery("Leaderboard.findLeaderboard", Leaderboard.class).setParameter("questionnaire", quest)
					.setHint("javax.persistence.cache.storeMode", "REFRESH").getResultList();
		} catch (PersistenceException e) {
			throw new PersistenceException("Could not get the leaderboard");
		}
		
		for (Leaderboard l : leaderboards) {
			User u = l.getUser();
			if (!users.contains(u))
				em.refresh(u);
				users.add(l.getUser());
		}
		
		return users;
	}
	
	public List<User> getUsersCancelled(int questionnaireID) {
		List<Leaderboard> leaderboards = new ArrayList<>();
		List<User> users = new ArrayList<>();
		
		Questionnaire quest = em.find(Questionnaire.class, questionnaireID);
		
		try {
			leaderboards = em.createNamedQuery("Leaderboard.findCancel", Leaderboard.class).setParameter("questionnaire", quest)
					.setHint("javax.persistence.cache.storeMode", "REFRESH").getResultList();
		} catch (PersistenceException e) {
			throw new PersistenceException("Could not get the leaderboard");
		}
		
		for (Leaderboard l : leaderboards) {
			User u = l.getUser();
			if (!users.contains(u))
				users.add(u);
		}
		
		return users;
	}
	
	public void userCancels(User user, int questionnaire) {
		Questionnaire quest = em.find(Questionnaire.class, questionnaire);
		Leaderboard leaderboard;
		
		try {
			leaderboard = em.createNamedQuery("Leaderboard.findUserCancel", Leaderboard.class).setParameter("questionnaire", quest).setParameter("user", user)
					.setHint("javax.persistence.cache.storeMode", "REFRESH").getSingleResult();
		} catch (PersistenceException e) {
			leaderboard = new Leaderboard(user, quest);
			em.merge(leaderboard);	
		}
	}
}
