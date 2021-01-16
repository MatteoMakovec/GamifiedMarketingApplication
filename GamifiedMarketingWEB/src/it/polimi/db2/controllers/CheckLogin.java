package it.polimi.db2.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.db2.entities.User;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.services.UserService;


@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB(name = "it.polimi.db2.services/UserService")
	private UserService usrService;
	
    public CheckLogin() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String usr = null;
		String pwd = null;
		
		try {
			usr = request.getParameter("usr");
			pwd = request.getParameter("pwd");
			if (usr == null || pwd == null || usr.isEmpty() || pwd.isEmpty()) {
				throw new Exception("Missing or empty credential value");
			}

		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}
		
		User user;
		try {
			user = usrService.checkCredentials(usr, pwd);
		} catch (CredentialsException | NonUniqueResultException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
			return;
		}
		
		String path;
		if (user == null) {
			PrintWriter out = response.getWriter();
			out.println("<p>Incorrect username or password</p>");
		} else {
			request.getSession().setAttribute("user", user);
			if (user.getType().equals("Admin")) {
				path = getServletContext().getContextPath() + "/AdminPage";
			}
			else {
				path = getServletContext().getContextPath() + "/Home";
			}
			response.sendRedirect(path);
		}
	}
	
	public void destroy() {}
}
