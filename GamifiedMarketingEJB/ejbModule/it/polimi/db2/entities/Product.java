package it.polimi.db2.entities;

import java.io.Serializable;
import java.util.Base64;

import javax.persistence.*;


@Entity
@Table(name = "Product", schema = "gamified_marketing")
@NamedQuery(name = "Product.getProduct", query = "SELECT p FROM Product p  WHERE p.p_name = :name")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_product")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID_product;
	
	private String p_name;

	@Basic(fetch=FetchType.EAGER)
	@Lob
	private byte[] image;
	

	public Product() {}
	
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

	public String getImage() {
		return Base64.getMimeEncoder().encodeToString(image);
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}