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
 * Servlet implementation class Servlet
 */
@WebServlet("/getDevices")
public class DevicesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final String  DBNAME = "nircompany";
	private static final String  TABLE_NAME = "devices";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DevicesServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Save/Get the DataCrud (using the same one and not creating a new crud each time)
		ServletContext context = request.getSession().getServletContext();
		DevicesCrud crud =  (DevicesCrud) context.getAttribute("DevicesCrud");
		if (null == crud) {
			crud = new DevicesCrud(DBNAME, TABLE_NAME);
			context.setAttribute("DevicesCrud", crud);
		}
		
		JSONArray jsonArray = crud.read(0);
		
		response.getWriter().print(jsonArray);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userID = request.getParameter("userID");
		String companyName = request.getParameter("companyName");
		String deviceName = request.getParameter("deviceName");
		String deviceID = request.getParameter("deviceID");
		RegisterDeviceData data = new RegisterDeviceData(userID, companyName, deviceName, deviceID);
				
		//Get the crud from ServletContext (instead of CrudPool)
		ServletContext context = request.getSession().getServletContext();
		DevicesCrud crud =  (DevicesCrud) context.getAttribute("DevicesCrud");
		if (null == crud) {
			crud = new DevicesCrud(DBNAME, TABLE_NAME);
			context.setAttribute("DevicesCrud", crud);
		}
				
		int valid =  crud.create(data);
		if (1 == valid || 2 == valid) { //Success
			response.getWriter().write("http://localhost:8080/DeviceRegister/devices-list2.html");
		}
		else {	//Error (got error code)	
			/* TODO  complete error handling*/
			response.getWriter().write(Integer.toString(valid));
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext context = request.getSession().getServletContext();
		DevicesCrud crud =  (DevicesCrud) context.getAttribute("DevicesCrud");
		if (null == crud) {
			crud = new DevicesCrud(DBNAME, TABLE_NAME);
			context.setAttribute("DevicesCrud", crud);
		}
		
		crud.delete(Integer.parseInt(request.getParameter("deviceID")));
	}
}
