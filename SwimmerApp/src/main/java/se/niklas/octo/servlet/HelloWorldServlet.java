package se.niklas.octo.servlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.niklas.octo.SwimmerApplication;
import se.niklas.octo.domain.PersonalBest;
import se.niklas.octo.domain.Swimmer;

public class HelloWorldServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String lineSep = System.getProperty("line.separator");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {
    	String attribute = (String) req.getParameter("action");
    	String firstName = (String) req.getParameter("firstname");
    	String secondName = (String) req.getParameter("secondname");
    	SwimmerApplication swimmApp = new SwimmerApplication();
    	List<Swimmer> foundSwimmer = swimmApp.searchSwimmer(firstName, secondName);
    	StringBuilder builder = new StringBuilder();
    	for (Swimmer swimmer : foundSwimmer) {
    		swimmApp.loadSwimmerFromOctoWeb(swimmer);
		}
    	for (Swimmer swimmer : swimmApp.getSwimmers()) {
			builder.append(swimmer + lineSep);
			for (PersonalBest best : swimmer.getPersonalBests()) {
				builder.append(best + lineSep);
			}
			
		}
    	
        resp.getOutputStream().write(builder.toString().getBytes());
    }
}