package it.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "Product", schema = "gamified_marketing")
@NamedQueries({
	@NamedQuery(name = "Product.getProduct", query = "SELECT p FROM Product p  WHERE p.p_name = :name"),
	@NamedQuery(name = "Product.getProductID", query = "SELECT p FROM Product p  WHERE p.ID_product = :ID")
})

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int ID_product;
	
	private String p_name;

	@Basic(fetch=FetchType.LAZY)
	private byte[] image;
	

	public Product() {
	}
	
	public Product(String name) {
		p_name = name;
	}
	
	public Product(String name, byte[] img) {
		p_name = name;
		image = img;
	}
	
	public int getID() {
		return this.ID_product;
	}

	public void setID(int ID_product) {
		this.ID_product = ID_product;
	}
	
	public String getName() {
		return this.p_name;
	}

	public void setName(String name) {
		this.p_name = name;
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}