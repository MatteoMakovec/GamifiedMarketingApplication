package it.polimi.db2.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.entities.Product;
import it.polimi.db2.services.ProductService;
import it.polimi.db2.services.QuestionnaireService;

@WebServlet("/CreateQuestionnaire")
public class CreateQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name = "it.polimi.db2.mission.services/QuestionnaireService")
	private QuestionnaireService questionnaireService;
	
	@EJB(name = "it.polimi.db2.mission.services/ProductService")
	private ProductService productService;

	
	public CreateQuestionnaire() {
		super();
	}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			String loginpath = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(loginpath);
			return;
		}
		
		int questionnaireID;
		String questionnaireDate = null;
		int productID;
		int numberOfQuestions;
		
		try {
			questionnaireID = Integer.parseInt(request.getParameter("questionnaireID"));
			questionnaireDate = StringEscapeUtils.escapeJava(request.getParameter("questionnaireDate"));
			productID = Integer.parseInt(request.getParameter("productID"));
			numberOfQuestions = Integer.parseInt(request.getParameter("numberOfQuestions"));
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect or missing param values");
			return;
		}
		
		List<String> s = new ArrayList<>();			// Trovare un modo più elegante per farlo
		for (int i=0; i<numberOfQuestions; i++) {
			s.add("q");
		}
		
		try {
			Product product = productService.getProduct(productID);
			questionnaireService.createQuestionnaire(questionnaireDate, product);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to create mission");
			return;
		}

		String path = "/WEB-INF/addQuestions.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("numberOfQuestions", s);

		templateEngine.process(path, ctx, response.getWriter());
	}

	public void destroy() {}
}
