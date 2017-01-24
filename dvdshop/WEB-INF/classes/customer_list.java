import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class customer_list extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		db data=new db();
		HttpSession session=request.getSession();
		if(session.getAttribute("username")!=null) {
			if(session.getAttribute("userrole").equals("manager")) {
				if(request.getParameterMap().containsKey("action")) {
					if(request.getParameter("action").toString().equals("customer_info")) {
						response.setContentType("text/html;charset=UTF-8");
						PrintWriter out = response.getWriter();
						out.println("<!DOCTYPE html>");
						out.println("<html><head>");
						out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
						out.println("<title>Customer Info - DVD Shop</title></head>");
						out.println("<style>.tb { border: 1px solid black; padding: 10px; display: inline-block; }</style>");
						out.println("<body>");
				
						out.println("<center>");
						out.println("<h1>Customer Info - DVD Shop</h1>");
						
						out.println("<form class=\"tb\">");
						accessControl ac=new accessControl();
						out.println(ac.menu(session.getAttribute("userrole").toString()));
						out.println("</form>");
				
						out.println("<form  style='display: table;'  method=\"post\" class=\"tb\">");
						
						try {
						String sql = "SELECT * FROM user WHERE id='"+request.getParameter("id").toString()+"'";
						ResultSet rs = data.get_data(sql);
						while(rs.next()){
							out.println("<p>Username: "+rs.getString("username")+"</p>");
							out.println("<p>Name: "+rs.getString("name")+"</p>");
							out.println("<p>DOB: "+rs.getString("dob")+"</p>");
							out.println("<p>Gender: "+rs.getString("gender")+"</p>");
							out.println("<p>Email: "+rs.getString("email")+"</p>");
							out.println("<p>Cell: "+rs.getString("cell")+"</p>");
						}
						}
						catch (Exception e) {
					
						}
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
					out.println("<title>Customer List - DVD Shop</title></head>");
					out.println("<style>.tb { border: 1px solid black; padding: 10px; }</style>");
					out.println("<body>");
					
					out.println("<center>");
					out.println("<h1>Customer List - DVD Shop</h1>");
					
					out.println("<form class=\"tb\">");
						accessControl ac=new accessControl();
						out.println(ac.menu(session.getAttribute("userrole").toString()));
						out.println("</form>");
					
					out.println("<form action='?action=search_user' method=\"post\" class=\"tb\">");
					out.println("<p>Search User: <input name=\"name\" type=\"text\" /> By ");
					out.println("<select name=\"category\">");
					out.println("<option>username</option>");
					out.println("<option>name</option>");
					out.println("<option>email</option>");
					out.println("</select></p>");
					
					out.println("<p><input type=\"submit\" value=\"Submit\" /></p>");
					out.println("</form>");
					
					out.println("<table class='tb'>");
					out.println("<tr><th class='tb'>ID</th><th class='tb'>Username</th><th class='tb'>Name</th><th class='tb'>Email</th><th class='tb'>Details</th></tr>");
					try {
					String sql = "SELECT * FROM user WHERE role='customer'";
					ResultSet rs = data.get_data(sql);
					while(rs.next()){
						out.println("<tr>");
						out.println("<td class='tb'>"+rs.getString("id")+"</td>");
						out.println("<td class='tb'>"+rs.getString("username")+"</td>");
						out.println("<td class='tb'>"+rs.getString("name")+"</td>");
						out.println("<td class='tb'>"+rs.getString("email")+"</td>");
						out.println("<td class='tb'><a href='?action=customer_info&id="+rs.getString("id")+"'>Details</a></td>");
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
			if(session.getAttribute("userrole").equals("manager")) {
				if(request.getParameterMap().containsKey("action")) {
					if(request.getParameter("action").toString().equals("search_user") && request.getParameterMap().containsKey("name")) {
						if(!request.getParameter("name").toString().equals("")) {
						String name=request.getParameter("name").toString();
						
						response.setContentType("text/html;charset=UTF-8");
						PrintWriter out = response.getWriter();
						out.println("<!DOCTYPE html>");
						out.println("<html><head>");
						out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
						out.println("<title>Customer List - DVD Shop</title></head>");
						out.println("<style>.tb { border: 1px solid black; padding: 10px; }</style>");
						out.println("<body>");
					
						out.println("<center>");
						out.println("<h1>Customer List - DVD Shop</h1>");
						
						out.println("<form class=\"tb\">");
						accessControl ac=new accessControl();
						out.println(ac.menu(session.getAttribute("userrole").toString()));
						out.println("</form>");
					
						out.println("<form action='?action=search_user' method=\"post\" class=\"tb\">");
						out.println("<p>Search User: <input name=\"name\" type=\"text\" /> By ");
						out.println("<select name=\"category\">");
						out.println("<option>username</option>");
						out.println("<option>name</option>");
						out.println("<option>email</option>");
						out.println("</select></p>");
					
						out.println("<p><input type=\"submit\" value=\"Submit\" /></p>");
						out.println("<p>Search Result for '"+name+"'</p>");
						out.println("</form>");
					
						out.println("<table class='tb'>");
						out.println("<tr><th class='tb'>ID</th><th class='tb'>Username</th><th class='tb'>Name</th><th class='tb'>Email</th><th class='tb'>Details</th></tr>");
						try {
							String sql = "SELECT * FROM user WHERE "+request.getParameter("category").toString()+" LIKE'%"+name+"%' AND role='customer'";
							ResultSet rs = data.get_data(sql);
							while(rs.next()){
							out.println("<tr>");
							out.println("<td class='tb'>"+rs.getString("id")+"</td>");
							out.println("<td class='tb'>"+rs.getString("username")+"</td>");
							out.println("<td class='tb'>"+rs.getString("name")+"</td>");
							out.println("<td class='tb'>"+rs.getString("email")+"</td>");
							out.println("<td class='tb'><a href='?action=customer_info&id="+rs.getString("id")+"'>Details</a></td>");
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
			}
		}
	}
}