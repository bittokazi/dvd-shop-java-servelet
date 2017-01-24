import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
   
public class dvd_category extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		db data=new db();
		HttpSession session=request.getSession();
		if(session.getAttribute("username")!=null) {
			if(session.getAttribute("userrole").equals("manager")) {
				if(request.getParameterMap().containsKey("action")) {
					if(request.getParameter("action").toString().equals("add_dvd")) {
						response.setContentType("text/html;charset=UTF-8");
						PrintWriter out = response.getWriter();
						out.println("<!DOCTYPE html>");
						out.println("<html><head>");
						out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
						out.println("<title>Add Category - DVD Shop</title></head>");
						out.println("<style>.tb { border: 1px solid black; padding: 10px; display: inline-block; }</style>");
						out.println("<body>");
				
						out.println("<center>");
						out.println("<h1>DVD Category - DVD Shop</h1>");
						
						out.println("<form class=\"tb\">");
						accessControl ac=new accessControl();
						out.println(ac.menu(session.getAttribute("userrole").toString()));
						out.println("</form>");
				
						out.println("<form style='display: table;' action='?action=add_category_action' method=\"post\" class=\"tb\">");
						out.println("<p>Category Name: <input name=\"name\" type=\"text\" /></p>");
						out.println("<p><input type=\"submit\" value=\"Submit\" /></p>");
						out.println("</form>");
				
						out.println("</body>");
						out.println("</html>");
					}
					if(request.getParameter("action").toString().equals("edit_category")) {
						response.setContentType("text/html;charset=UTF-8");
						PrintWriter out = response.getWriter();
						out.println("<!DOCTYPE html>");
						out.println("<html><head>");
						out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
						out.println("<title>Edit Category - DVD Shop</title></head>");
						out.println("<style>.tb { border: 1px solid black; padding: 10px; display: inline-block; }</style>");
						out.println("<body>");
				
						out.println("<center>");
						out.println("<h1>Edit DVD Category - DVD Shop</h1>");
						
						out.println("<form class=\"tb\">");
						accessControl ac=new accessControl();
						out.println(ac.menu(session.getAttribute("userrole").toString()));
						out.println("</form>");
				
						out.println("<form  style='display: table;'  action='?action=edit_category_action&id="+request.getParameter("id").toString()+"' method=\"post\" class=\"tb\">");
						
						try {
						String sql = "SELECT * FROM category WHERE id='"+request.getParameter("id").toString()+"'";
						ResultSet rs = data.get_data(sql);
						while(rs.next()){
							out.println("<p>Category Name: <input name=\"name\" value='"+rs.getString("name")+"' type=\"text\" /></p>");
							out.println("<p><input type=\"submit\" value=\"Submit\" /></p>");
						}
						}
						catch (Exception e) {
					
						}
						out.println("</form>");
				
						out.println("</body>");
						out.println("</html>");
					}
					if(request.getParameter("action").toString().equals("dvd_list")) {
						String category="";
						if(request.getParameterMap().containsKey("id")) category=request.getParameter("id").toString();
						response.setContentType("text/html;charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<!DOCTYPE html>");
					out.println("<html><head>");
					out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
					out.println("<title>DVD List - DVD Shop</title></head>");
					out.println("<style>.tb { border: 1px solid black; padding: 10px; }</style>");
					out.println("<body>");
					
					out.println("<center>");
					out.println("<h1>DVD List - DVD Shop</h1>");
					
					out.println("<form class=\"tb\">");
					accessControl ac=new accessControl();
					out.println(ac.menu(session.getAttribute("userrole").toString()));
					out.println("</form>");
					
					out.println("<table class='tb'>");
					out.println("<tr><th class='tb'>ID</th><th class='tb'>Title</th><th class='tb'>Category</th><th class='tb'>Actors</th><th class='tb'>Date Added</th><th class='tb'>Edit</th><th class='tb'>Delete</th></tr>");
					try {
					String sql = "SELECT * FROM dvd WHERE category_id='"+category+"'";
					ResultSet rs = data.get_data(sql);
					while(rs.next()){
						out.println("<tr>");
						out.println("<td class='tb'>"+rs.getString("id")+"</td>");
						out.println("<td class='tb'>"+rs.getString("title")+"</td>");
						out.println("<td class='tb'>"+rs.getString("category_id")+"</td>");
						out.println("<td class='tb'>"+rs.getString("cast")+"</td>");
						out.println("<td class='tb'>"+rs.getString("date")+"</td>");
						out.println("<td class='tb'><a href='dvd_list?action=edit_dvd&id="+rs.getString("id")+"'>Edit</a></td>");
						out.println("<td class='tb'><a href='dvd_list?action=delete_dvd&id="+rs.getString("id")+"'>Delete</a></td>");
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
					if(request.getParameter("action").toString().equals("delete_category") && request.getParameterMap().containsKey("id")) {
						if(!request.getParameter("id").toString().equals("")) {
						String sql;
						sql = "DELETE FROM category WHERE id = '"+request.getParameter("id").toString()+"'";
						if(data.delete_data(sql).equals("ok")) {
							response.setContentType("text/html;charset=UTF-8");
							PrintWriter out = response.getWriter();
							out.println("Deleted Category Successfully");
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
							out.println("EMPTY ID");
						}
					}
				}
				else {
					response.setContentType("text/html;charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<!DOCTYPE html>");
					out.println("<html><head>");
					out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
					out.println("<title>DVD Category - DVD Shop</title></head>");
					out.println("<style>.tb { border: 1px solid black; padding: 10px; }</style>");
					out.println("<body>");
					
					out.println("<center>");
					out.println("<h1>DVD Category - DVD Shop</h1>");
					
					out.println("<form class=\"tb\">");
					accessControl ac=new accessControl();
					out.println(ac.menu(session.getAttribute("userrole").toString()));
					out.println("</form>");
					
					out.println("<table class='tb'>");
					out.println("<tr><th class='tb'>ID</th><th class='tb'>Name</th><th class='tb'>DVD List</th><th class='tb'>Edit</th><th class='tb'>Delete</th></tr>");
					try {
					String sql = "SELECT * FROM Category";
					ResultSet rs = data.get_data(sql);
					while(rs.next()){
						out.println("<tr>");
						out.println("<td class='tb'>"+rs.getString("id")+"</td>");
						out.println("<td class='tb'>"+rs.getString("name")+"</td>");
						out.println("<td class='tb'><a href='?action=dvd_list&id="+rs.getString("name")+"'>List</a></td>");
						out.println("<td class='tb'><a href='?action=edit_category&id="+rs.getString("id")+"'>Edit</a></td>");
						out.println("<td class='tb'><a href='?action=delete_category&id="+rs.getString("id")+"'>Delete</a></td>");
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
					if(request.getParameter("action").toString().equals("add_dvd")) {
						response.setContentType("text/html;charset=UTF-8");
						PrintWriter out = response.getWriter();
						out.println("Access Denied");
					}
					if(request.getParameter("action").toString().equals("edit_category")) {
						response.setContentType("text/html;charset=UTF-8");
						PrintWriter out = response.getWriter();
						out.println("Access Denied");
					}
					if(request.getParameter("action").toString().equals("dvd_list")) {
						String category="";
						if(request.getParameterMap().containsKey("id")) category=request.getParameter("id").toString();
						response.setContentType("text/html;charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<!DOCTYPE html>");
					out.println("<html><head>");
					out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
					out.println("<title>DVD List - DVD Shop</title></head>");
					out.println("<style>.tb { border: 1px solid black; padding: 10px; }</style>");
					out.println("<body>");
					
					out.println("<center>");
					out.println("<h1>DVD List - DVD Shop</h1>");
					
					out.println("<form class=\"tb\">");
					accessControl ac=new accessControl();
					out.println(ac.menu(session.getAttribute("userrole").toString()));
					out.println("</form>");
					
					out.println("<table class='tb'>");
					out.println("<tr><th class='tb'>ID</th><th class='tb'>Title</th><th class='tb'>Category</th><th class='tb'>Actors</th><th class='tb'>Date Added</th></tr>");
					try {
					String sql = "SELECT * FROM dvd WHERE category_id='"+category+"'";
					ResultSet rs = data.get_data(sql);
					while(rs.next()){
						out.println("<tr>");
						out.println("<td class='tb'>"+rs.getString("id")+"</td>");
						out.println("<td class='tb'>"+rs.getString("title")+"</td>");
						out.println("<td class='tb'>"+rs.getString("category_id")+"</td>");
						out.println("<td class='tb'>"+rs.getString("cast")+"</td>");
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
				else {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<!DOCTYPE html>");
				out.println("<html><head>");
				out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
				out.println("<title>DVD Category - DVD Shop</title></head>");
				out.println("<style>.tb { border: 1px solid black; padding: 10px; }</style>");
				out.println("<body>");
					
				out.println("<center>");
				out.println("<h1>DVD Category - DVD Shop</h1>");
				
				out.println("<form class=\"tb\">");
				accessControl ac=new accessControl();
				out.println(ac.menu(session.getAttribute("userrole").toString()));
				out.println("</form>");
					
				out.println("<table class='tb'>");
				out.println("<tr><th class='tb'>ID</th><th class='tb'>Name</th><th class='tb'>DVD List</th></tr>");
				try {
					String sql = "SELECT * FROM Category";
					ResultSet rs = data.get_data(sql);
					while(rs.next()){
						out.println("<tr>");
						out.println("<td class='tb'>"+rs.getString("id")+"</td>");
						out.println("<td class='tb'>"+rs.getString("name")+"</td>");
						out.println("<td class='tb'><a href='?action=dvd_list&id="+rs.getString("name")+"'>List</a></td>");
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
					if(request.getParameter("action").toString().equals("add_category_action") && request.getParameterMap().containsKey("name")) {
						if(!request.getParameter("name").toString().equals("")) {
						String name=request.getParameter("name").toString();
						String sql;
						sql = "INSERT INTO category VALUES('0','"+name+"','')";
						if(data.insert_data(sql).equals("ok")) {
							response.setContentType("text/html;charset=UTF-8");
							PrintWriter out = response.getWriter();
							out.println("Added Category Successfully");
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
							out.println("EMPTY NAME");
						}
					}
					if(request.getParameter("action").toString().equals("edit_category_action") && request.getParameterMap().containsKey("name")) {
						if(!request.getParameter("name").toString().equals("")) {
						String name=request.getParameter("name").toString();
						String sql;
						sql = "UPDATE category SET name='"+name+"' WHERE id='"+request.getParameter("id").toString()+"'";
						if(data.update_data(sql).equals("ok")) {
							response.setContentType("text/html;charset=UTF-8");
							PrintWriter out = response.getWriter();
							out.println("Updated Category Successfully");
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
							out.println("EMPTY NAME");
						}
					}
				}
			}
			if(session.getAttribute("userrole").equals("customer")) {
				if(request.getParameterMap().containsKey("action")) {
					if(request.getParameter("action").toString().equals("add_category_action")) {
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