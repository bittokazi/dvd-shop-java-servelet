import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class dvd_request extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		db data=new db();
		HttpSession session=request.getSession();
		if(session.getAttribute("username")!=null) {
			if(session.getAttribute("userrole").equals("manager")) {
				if(request.getParameterMap().containsKey("action")) {
					if(request.getParameter("action").toString().equals("add_dvd_request")) {
						response.setContentType("text/html;charset=UTF-8");
						PrintWriter out = response.getWriter();
						out.println("Access Denied");
					}
				}
				else {
					response.setContentType("text/html;charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<!DOCTYPE html>");
					out.println("<html><head>");
					out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
					out.println("<title>Customer DVD Request - DVD Shop</title></head>");
					out.println("<style>.tb { border: 1px solid black; padding: 10px; }</style>");
					out.println("<body>");
					
					out.println("<center>");
					out.println("<h1>Customer DVD Requests - DVD Shop</h1>");
					
					out.println("<form class=\"tb\">");
						accessControl ac=new accessControl();
						out.println(ac.menu(session.getAttribute("userrole").toString()));
						out.println("</form>");
					
					out.println("<table class='tb'>");
					out.println("<tr><th class='tb'>ID</th><th class='tb'>Title</th><th class='tb'>Description</th><th class='tb'>User ID</th><th class='tb'>Date</th></tr>");
					try {
					String sql = "SELECT * FROM request";
					ResultSet rs = data.get_data(sql);
					while(rs.next()){
						out.println("<tr>");
						out.println("<td class='tb'>"+rs.getString("id")+"</td>");
						out.println("<td class='tb'>"+rs.getString("title")+"</td>");
						out.println("<td class='tb'>"+rs.getString("description")+"</td>");
						out.println("<td class='tb'>"+rs.getString("user_id")+"</td>");
						out.println("<td class='tb'>"+rs.getString("date")+"</td>");
						out.println("</tr>");
					}
					}
					catch (Exception e) {
					
					}
					out.println("</table>");
					out.println("</center>");
					out.println("</body>");
					out.println("</html>");
				}
			}
			if(session.getAttribute("userrole").equals("customer")) {
				if(request.getParameterMap().containsKey("action")) {
					if(request.getParameter("action").toString().equals("add_dvd_request")) {
						response.setContentType("text/html;charset=UTF-8");
						PrintWriter out = response.getWriter();
						out.println("<!DOCTYPE html>");
						out.println("<html><head>");
						out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
						out.println("<title>Add DVD Request - DVD Shop</title></head>");
						out.println("<style>.tb { border: 1px solid black; padding: 10px; display: inline-block; }</style>");
						out.println("<body>");
				
						out.println("<center>");
						out.println("<h1>Add DVD Request - DVD Shop</h1>");
						
						out.println("<form class=\"tb\">");
						accessControl ac=new accessControl();
						out.println(ac.menu(session.getAttribute("userrole").toString()));
						out.println("</form>");
				
						out.println("<form  style='display: table;'  action='?action=add_dvd_request_action' method=\"post\" class=\"tb\">");
						out.println("<p>Title: <input name=\"name\" type=\"text\" /></p>");
						out.println("<p>Description: <textarea name='description'></textarea></p>");
						out.println("<p><input type=\"submit\" value=\"Submit\" /></p>");
						out.println("</form>");
				
						out.println("</body>");
						out.println("</html>");
					}
				}
				else {
					response.setContentType("text/html;charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<!DOCTYPE html>");
					out.println("<html><head>");
					out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
					out.println("<title>DVD Request - DVD Shop</title></head>");
					out.println("<style>.tb { border: 1px solid black; padding: 10px; }</style>");
					out.println("<body>");
					
					out.println("<center>");
					out.println("<h1>Your DVD Requests - DVD Shop</h1>");
					
					out.println("<form  class=\"tb\">");
						accessControl ac=new accessControl();
						out.println(ac.menu(session.getAttribute("userrole").toString()));
						out.println("</form>");
					
					out.println("<table class='tb'>");
					out.println("<tr><th class='tb'>ID</th><th class='tb'>Title</th><th class='tb'>Description</th><th class='tb'>Date</th></tr>");
					try {
					String sql = "SELECT * FROM request WHERE user_id='"+session.getAttribute("username").toString()+"'";
					ResultSet rs = data.get_data(sql);
					while(rs.next()){
						out.println("<tr>");
						out.println("<td class='tb'>"+rs.getString("id")+"</td>");
						out.println("<td class='tb'>"+rs.getString("title")+"</td>");
						out.println("<td class='tb'>"+rs.getString("description")+"</td>");
						out.println("<td class='tb'>"+rs.getString("date")+"</td>");
						out.println("</tr>");
					}
					}
					catch (Exception e) {
					
					}
					out.println("</table>");
					out.println("</center>");
					out.println("</body>");
					out.println("</html>");
				}
			}
		}
		else {
			response.sendRedirect("/dvdshop/login");
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session=request.getSession();
		db data=new db();
		if(session.getAttribute("username")!=null) {
			if(session.getAttribute("userrole").equals("customer")) {
				if(request.getParameterMap().containsKey("action")) {
					if(request.getParameter("action").toString().equals("add_dvd_request_action") && request.getParameterMap().containsKey("name")) {
						if(!request.getParameter("name").toString().equals("")) {
						String name=request.getParameter("name").toString();
						String description=request.getParameter("description").toString();
						
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	                    Date date = new Date();
						
						String sql;
						sql = "INSERT INTO request VALUES('0','"+session.getAttribute("username").toString()+"','"+name+"','"+description+"','"+dateFormat.format(date)+"')";
						if(data.insert_data(sql).equals("ok")) {
							response.setContentType("text/html;charset=UTF-8");
							PrintWriter out = response.getWriter();
							out.println("Added Request Successfully");
						}
						else {
							response.setContentType("text/html;charset=UTF-8");
							PrintWriter out = response.getWriter();
							out.println("Error Occured");
						}
						}
						else {
							response.setContentType("text/html;charset=UTF-8");
							PrintWriter out = response.getWriter();
							out.println("EMPTY Title");
						}
					}
				}
			}
			if(session.getAttribute("userrole").equals("manager")) {
				if(request.getParameterMap().containsKey("action")) {
					if(request.getParameter("action").toString().equals("add_dvd_request_action")) {
						response.setContentType("text/html;charset=UTF-8");
						PrintWriter out = response.getWriter();
						out.println("Access Denied");
					}
				}
			}
		}
		else {
			response.sendRedirect("/dvdshop/login");
		}
	}
}