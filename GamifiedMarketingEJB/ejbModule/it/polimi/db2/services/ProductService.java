package it.polimi.db2.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.Product;

@Stateless
public class ProductService {
	@PersistenceContext(unitName = "GamifiedMarketingApplication")
	private EntityManager em;

	public ProductService() {}

	public Product getProduct(String name){
		List<Product> products = new ArrayList<>();
		try {
			products = em.createNamedQuery("Product.getProduct", Product.class).setParameter("name", name)
					.getResultList();
		} catch (PersistenceException e) {
			throw new PersistenceException("Could not get the product");
		}
		
		if (products.isEmpty())
			return null;
		else 
			return products.get(products.size()-1);
	}
	
	public Product getProduct(int ID){
		Product product = null;
		try {
			product = em.createNamedQuery("Product.getProductID", Product.class).setParameter("ID", ID)
					.getSingleResult();
		} catch (PersistenceException e) {
			throw new PersistenceException("Could not get the product");
		}
		
		if (product == null)
			return null;
		else 
			return product;
	}
	
	public void createProduct(String name){
		Product product = new Product(name);
		
		em.persist(product);
	}
	
	
	public void createProduct(String name, byte[] image){
		Product product = new Product(name, image);
		
		em.persist(product);
	}
}
