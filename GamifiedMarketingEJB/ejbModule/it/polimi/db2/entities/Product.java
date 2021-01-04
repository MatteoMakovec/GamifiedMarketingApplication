package it.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "Product", schema = "gamified_marketing")
@NamedQuery(name = "Product.getProduct", query = "SELECT p FROM Product p  WHERE p.p_name = :name")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String p_name;

	@Basic(fetch=FetchType.LAZY)
	private byte[] image;
	

	public Product() {
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