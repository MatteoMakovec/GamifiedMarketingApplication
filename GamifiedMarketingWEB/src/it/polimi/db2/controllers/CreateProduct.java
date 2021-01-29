package it.polimi.db2.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.db2.services.ProductService;
import it.polimi.db2.utils.*;

@WebServlet("/CreateProduct")
@MultipartConfig
public class CreateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "it.polimi.db2.mission.services/ProductService")
	private ProductService productService;

	
	public CreateProduct() {
		super();
	}

	public void init() throws ServletException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			String loginpath = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(loginpath);
			return;
		}

		String productName = null;
		byte[] imgByteArray = null;
		try {
			productName = StringEscapeUtils.escapeJava(request.getParameter("productName"));
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect or missing param values");
			return;
		}
		
		Part imgFile = request.getPart("imageUpload");
		InputStream imgContent = imgFile.getInputStream();
		imgByteArray = ImageUtils.readImage(imgContent);
		//imgByteArray = request.getParameter("imageUpload").getBytes();
		if (imgByteArray.length == 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid photo parameters");
		}

		try {
			productService.createProduct(productName, imgByteArray);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to create mission");
			return;
		}

		String ctxpath = getServletContext().getContextPath();
		String path = ctxpath + "/AdminPage";
		response.sendRedirect(path);
	}

	public void destroy() {}
}
