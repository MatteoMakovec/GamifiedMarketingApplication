package it.polimi.db2.controllers;

import java.io.IOException;
import java.sql.Date;

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

import it.polimi.db2.services.ProductService;
import it.polimi.db2.services.QuestionService;
import it.polimi.db2.services.QuestionnaireService;


@WebServlet("/SubmitQuestions")
public class SubmitQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name = "it.polimi.db2.services/QuestionService")
	private QuestionService questionService;
	
	@EJB(name = "it.polimi.db2.services/QuestionnaireService")
	private QuestionnaireService questionnaireService;
	
	@EJB(name = "it.polimi.db2.services/ProductService")
	private ProductService productService;
	
	public SubmitQuestions() {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(loginpath);
			return;
		}
		
		String questionnaireDate = null;
		int productID;
		String[] questions = request.getParameterValues("question");
		try {
			questionnaireDate = StringEscapeUtils.escapeJava(request.getParameter("questionnaireDate"));
			productID = Integer.parseInt(request.getParameter("productID"));
			Date systemDate = new java.sql.Date(System.currentTimeMillis());
			Date date = Date.valueOf(questionnaireDate);
			if(date.compareTo(systemDate) < 0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You can only create questionnaires for a future date");
				return;
		    }
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect or missing param values");
			return;
		}
		
		questionnaireService.createQuestionnaire(questionnaireDate, productID, questions);
		
		String path = "/WEB-INF/adminPage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
	}

}
