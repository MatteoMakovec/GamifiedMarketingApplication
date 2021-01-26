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
import it.polimi.db2.exceptions.BadWordException;
import it.polimi.db2.exceptions.QuestionException;
import it.polimi.db2.services.AnswerService;
import it.polimi.db2.services.UserService;


@WebServlet("/SubmitAnswers")
public class SubmitAnswers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name = "it.polimi.db2.services/AnswerService")
	private AnswerService answerService;
	
	@EJB(name = "it.polimi.db2.services/UserService")
	private UserService userService;
	
	public SubmitAnswers() {
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
		
		List<Question> questions = new ArrayList<>();
		for (int i=0; i<(int)session.getAttribute("#question"); i++) {
			questions.add((Question) session.getAttribute("question"+i));
		}
		
		
		if ((questions!=null)&&(answers!=null)) {
			try {
				for (int i=0; i<answers.length; i++) {
					answerService.reportAnswer(answers[i], user, questions.get(i));
				}
			} catch (QuestionException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
				return;
			} catch (BadWordException e) {
				user.setType("Blocked");
				try {
					userService.updateUser(user);
				} catch (Exception e1) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, e1.getMessage());
					return;
				}
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
				return;
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
				return;
			}
		}
		
		
		String path = "/WEB-INF/greetingsPage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {}
}
