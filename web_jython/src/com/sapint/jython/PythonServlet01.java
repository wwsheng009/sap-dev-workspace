package com.sapint.jython;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PythonServlet01
 */
@WebServlet("/PythonServlet01")
public class PythonServlet01 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public PythonServlet01() {
        // TODO Auto-generated constructor stub
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
    	        response.setContentType("text/html;charset=UTF-8");
    	        PrintWriter out = response.getWriter();
    	        try {
    	            out.println("<html>");
    	            out.println("<head>");
    	            out.println("<title>Servlet NewJavaServlet Test</title>");
    	            out.println("</head>");
    	            out.println("<body>");
    	            out.println("<h1>Servlet NewJavaServlet at " +
    	            request.getContextPath () + "</h1>");
    	            out.println("</body>");
    	            out.println("</html>");
    	        } finally {
    	            out.close();
    	        }
    	    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 processRequest(request, response);
	}
	
	@Override
    public String getServletInfo() {
        return "Short description";
    }

}
