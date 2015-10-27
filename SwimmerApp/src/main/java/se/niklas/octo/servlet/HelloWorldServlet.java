package se.niklas.octo.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.niklas.octo.SwimmerApplication;
import se.niklas.octo.domain.Swimmer;

public class HelloWorldServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {
    	SwimmerApplication swimmApp = new SwimmerApplication();
    	List<Swimmer> searchSwimmer = swimmApp.searchSwimmer("Elias", "Skeppstedt");
    	StringBuilder builder = new StringBuilder();
    	for (Swimmer swimmer : searchSwimmer) {
    		swimmApp.loadSwimmerFromOctoWeb(swimmer);
		}
    	for (Swimmer swimmer : swimmApp.getSwimmers()) {
			builder.append(swimmer);
		}
    	
        resp.getOutputStream().write(builder.toString().getBytes());
    }
}