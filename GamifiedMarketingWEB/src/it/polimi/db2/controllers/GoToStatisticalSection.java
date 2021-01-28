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

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.entities.Question;
import it.polimi.db2.entities.User;
import it.polimi.db2.services.AnswerService;
import it.polimi.db2.services.QuestionService;
import it.polimi.db2.services.UserService;


@WebServlet("/StatisticalSection")
public class GoToStatisticalSection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name = "it.polimi.db2.services/UserService")
	private UserService userService;
	
	@EJB(name = "it.polimi.db2.services/QuestionService")
	private QuestionService questionService;
	
	public GoToStatisticalSection() {
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
		
		String[] answers = request.getParameterValues("answer");
		User user = (User) session.getAttribute("user");
		
		AnswerService answerService = (AnswerService) request.getSession().getAttribute("AnswerService");
		answerService.addAnswers(answers, user);
		
		int questionnaire;
		List<Question> questions = new ArrayList<>();
		try {
			questionnaire = (int) session.getAttribute("questionnaireID");
			questions = questionService.findStatisticalQuestions(questionnaire);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to get data");
			return;
		}
		
		answerService.setQuestions(questions);
		
		String path = "/WEB-INF/statisticalSection.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("questions", questions);
		ctx.setVariable("questionnaire", "Questionnaire: "+questionnaire);
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {}
}
