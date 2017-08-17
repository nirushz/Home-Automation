package com.ilrd.javascript_to_tomcat;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

/**
 * Servlet implementation class DataServlet
 */
@WebServlet("/getData")
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String  DB_NAME = "nircompany";
	private static final String  TABLE_NAME = "data";

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Save/Get the DataCrud (using the same one and not creating a new crud each time)
		ServletContext context = request.getSession().getServletContext();
		Object crud =  context.getAttribute("DataCrud");
		if (null == crud) {
			System.out.println("GET DataCrud crud is null"); /*TODO remove */
			crud = new DataCrud(DB_NAME, TABLE_NAME);
			context.setAttribute("DataCrud", crud);
		}
		
		int deviceID = Integer.parseInt(request.getParameter("deviceID"));
		System.out.println("deviceID=" + deviceID);
		
		JSONArray jsonArray = ((DataCrud) crud).read(deviceID);
		System.out.println("DataCrud GET="+ crud);
		
		//response.setContentType("application/json");
		response.getWriter().print(jsonArray);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
