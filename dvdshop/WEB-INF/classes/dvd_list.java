import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
   
public class dvd_list extends HttpServlet {
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
						out.println("<title>Add DVD - DVD Shop</title></head>");
						out.println("<style>.tb { border: 1px solid black; padding: 10px; display: inline-block; }</style>");
						out.println("<body>");
				
						out.println("<center>");
						out.println("<h1>Add DVD - DVD Shop</h1>");
						
						out.println("<form class=\"tb\">");
						accessControl ac=new accessControl();
						out.println(ac.menu(session.getAttribute("userrole").toString()));
						out.println("</form>");
				
						out.println("<form  style='display: table;' action='?action=add_dvd_action' method=\"post\" class=\"tb\">");
						out.println("<p>Title: <input name=\"name\" type=\"text\" /></p>");
						out.println("<p>Category: <select name=\"category\">");
						
						
						try {
						String sql = "SELECT * FROM category";
						ResultSet rs = data.get_data(sql);
						while(rs.next()){
							out.println("<option value='"+rs.getString("name")+"'>"+rs.getString("name")+"</option>");
						}
						}
						catch (Exception e) {
					
						}
						
						out.println("</select></p>");
						
						out.println("<p>Description: <textarea name='description'></textarea></p>");
						out.println("<p>Casting: <textarea name='cast'></textarea></p>");
						out.println("<p>Team: <textarea name='team'></textarea></p>");
						out.println("<p><input type=\"submit\" value=\"Submit\" /></p>");
						out.println("</form>");
				
						out.println("</body>");
						out.println("</html>");
					}
					if(request.getParameter("action").toString().equals("edit_dvd")) {
						response.setContentType("text/html;charset=UTF-8");
						PrintWriter out = response.getWriter();
						out.println("<!DOCTYPE html>");
						out.println("<html><head>");
						out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
						out.println("<title>Edit DVD - DVD Shop</title></head>");
						out.println("<style>.tb { border: 1px solid black; padding: 10px; display: inline-block; }</style>");
						out.println("<body>");
				
						out.println("<center>");
						out.println("<h1>Edit DVD Information - DVD Shop</h1>");
						
						out.println("<form class=\"tb\">");
						accessControl ac=new accessControl();
						out.println(ac.menu(session.getAttribute("userrole").toString()));
						out.println("</form>");
				
						out.println("<form  style='display: table;' action='?action=edit_dvd_action&id="+request.getParameter("id").toString()+"' method=\"post\" class=\"tb\">");
						
						try {
						String sql = "SELECT * FROM dvd WHERE id='"+request.getParameter("id").toString()+"'";
						ResultSet rs = data.get_data(sql);
						while(rs.next()){
							out.println("<p>Category Name: <input name=\"name\" value='"+rs.getString("title")+"' type=\"text\" /></p>");
							
							out.println("<p>Title: <input name=\"name\" value='"+rs.getString("title")+"' type=\"text\" /></p>");
							out.println("<p>Category: <select name=\"category\">");
							out.println("<option value='"+rs.getString("category_id")+"'>"+rs.getString("category_id")+"</option>");
						
							try {
								String sql1 = "SELECT * FROM category";
								ResultSet rs1 = data.get_data(sql1);
								while(rs1.next()){
									out.println("<option value='"+rs1.getString("name")+"'>"+rs1.getString("name")+"</option>");
								}
							}
							catch (Exception e) {
					
							}
						
							out.println("</select></p>");
						
							out.println("<p>Description: <textarea name='description'>"+rs.getString("description")+"</textarea></p>");
							out.println("<p>Casting: <textarea name='cast'>"+rs.getString("cast")+"</textarea></p>");
							out.println("<p>Team: <textarea name='team'>"+rs.getString("team")+"</textarea></p>");
							out.println("<p><input type=\"submit\" value=\"Submit\" /></p>");
						}
						}
						catch (Exception e) {
					
						}
						out.println("</form>");
				
						out.println("</body>");
						out.println("</html>");
					}
					if(request.getParameter("action").toString().equals("delete_dvd") && request.getParameterMap().containsKey("id")) {
						if(!request.getParameter("id").toString().equals("")) {
						String sql;
						sql = "DELETE FROM dvd WHERE id = '"+request.getParameter("id").toString()+"'";
						if(data.delete_data(sql).equals("ok")) {
							response.setContentType("text/html;charset=UTF-8");
							PrintWriter out = response.getWriter();
							out.println("Deleted DVD Successfully");
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
					if(request.getParameter("action").toString().equals("search_dvd")) {
						response.sendRedirect("/dvdshop/dvd_list");
					}
				}
				else {
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
					
					out.println("<form action='?action=search_dvd' method=\"post\" class=\"tb\">");
					out.println("<p>Search DVD: <input name=\"name\" type=\"text\" /> By ");
					out.println("<select name=\"category\">");
					out.println("<option>title</option>");
					out.println("<option value='category_id'>category</option>");
					out.println("<option>cast</option>");
					out.println("</select></p>");
					
						out.println("<p><input type=\"submit\" value=\"Submit\" /></p>");
						out.println("</form>");
					
					out.println("<table class='tb'>");
					out.println("<tr><th class='tb'>ID</th><th class='tb'>Title</th><th class='tb'>Category</th><th class='tb'>Actors</th><th class='tb'>Date Added</th><th class='tb'>Edit</th><th class='tb'>Delete</th></tr>");
					try {
					String sql = "SELECT * FROM dvd";
					ResultSet rs = data.get_data(sql);
					while(rs.next()){
						out.println("<tr>");
						out.println("<td class='tb'>"+rs.getString("id")+"</td>");
						out.println("<td class='tb'>"+rs.getString("title")+"</td>");
						out.println("<td class='tb'>"+rs.getString("category_id")+"</td>");
						out.println("<td class='tb'>"+rs.getString("cast")+"</td>");
						out.println("<td class='tb'>"+rs.getString("date")+"</td>");
						out.println("<td class='tb'><a href='?action=edit_dvd&id="+rs.getString("id")+"'>Edit</a></td>");
						out.println("<td class='tb'><a href='?action=delete_dvd&id="+rs.getString("id")+"'>Delete</a></td>");
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
					if(request.getParameter("action").toString().equals("edit_dvd")) {
						response.setContentType("text/html;charset=UTF-8");
						PrintWriter out = response.getWriter();
						out.println("Access Denied");
					}
					if(request.getParameter("action").toString().equals("search_dvd")) {
						response.sendRedirect("/dvdshop/dvd_list");
					}
				}
				else {
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
					
					out.println("<form action='?action=search_dvd' method=\"post\" class=\"tb\">");
					out.println("<p>Search DVD: <input name=\"name\" type=\"text\" /> By ");
					out.println("<select name=\"category\">");
					out.println("<option>title</option>");
					out.println("<option value='category_id'>category</option>");
					out.println("<option>cast</option>");
					out.println("</select></p>");
					out.println("<p><input type=\"submit\" value=\"Submit\" /></p>");
					out.println("</form>");
					
					
					out.println("<table class='tb'>");
					out.println("<tr><th class='tb'>ID</th><th class='tb'>Title</th><th class='tb'>Category</th><th class='tb'>Actors</th><th class='tb'>Date Added</th></tr>");
					try {
					String sql = "SELECT * FROM dvd";
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
					if(request.getParameter("action").toString().equals("add_dvd_action") && request.getParameterMap().containsKey("name")) {
						if(!request.getParameter("name").toString().equals("")) {
						String name=request.getParameter("name").toString();
						String description=request.getParameter("description").toString();
						String category=request.getParameter("category").toString();
						String cast=request.getParameter("cast").toString();
						String team=request.getParameter("team").toString();
						
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	                    Date date = new Date();
						
						String sql;
						sql = "INSERT INTO dvd VALUES('0','"+name+"','"+description+"','"+dateFormat.format(date)+"','"+category+"','"+cast+"','"+team+"','')";
						if(data.insert_data(sql).equals("ok")) {
							response.setContentType("text/html;charset=UTF-8");
							PrintWriter out = response.getWriter();
							out.println("Added DVD Successfully");
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
							out.println("EMPTY TITLE");
						}
					}
					if(request.getParameter("action").toString().equals("edit_dvd_action") && request.getParameterMap().containsKey("name")) {
						if(!request.getParameter("name").toString().equals("")) {
						String name=request.getParameter("name").toString();
						String description=request.getParameter("description").toString();
						String category=request.getParameter("category").toString();
						String cast=request.getParameter("cast").toString();
						String team=request.getParameter("team").toString();
						
						
						String sql;
						sql = "UPDATE dvd SET title='"+name+"',description='"+description+"',category_id='"+category+"',cast='"+cast+"',team='"+team+"' WHERE id='"+request.getParameter("id").toString()+"'";
						if(data.update_data(sql).equals("ok")) {
							response.setContentType("text/html;charset=UTF-8");
							PrintWriter out = response.getWriter();
							out.println("Updated DVD information Successfully");
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
					if(request.getParameter("action").toString().equals("search_dvd") && request.getParameterMap().containsKey("name")) {
						if(!request.getParameter("name").toString().equals("")) {
						String name=request.getParameter("name").toString();
						
						
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
					
					out.println("<form action='?action=search_dvd' method=\"post\" class=\"tb\">");
					out.println("<p>Search DVD: <input name=\"name\" type=\"text\" /> By ");
					out.println("<select name=\"category\">");
					out.println("<option>title</option>");
					out.println("<option value='category_id'>category</option>");
					out.println("<option>cast</option>");
					out.println("</select></p>");
					
						out.println("<p><input type=\"submit\" value=\"Submit\" /></p>");
						out.println("<p>Search Result for '"+name+"'</p>");
						out.println("</form>");
					
					out.println("<table class='tb'>");
					out.println("<tr><th class='tb'>ID</th><th class='tb'>Title</th><th class='tb'>Category</th><th class='tb'>Actors</th><th class='tb'>Date Added</th><th class='tb'>Edit</th><th class='tb'>Delete</th></tr>");
					try {
					String sql = "SELECT * FROM dvd WHERE "+request.getParameter("category").toString()+" LIKE'%"+name+"%'";
					ResultSet rs = data.get_data(sql);
					while(rs.next()){
						out.println("<tr>");
						out.println("<td class='tb'>"+rs.getString("id")+"</td>");
						out.println("<td class='tb'>"+rs.getString("title")+"</td>");
						out.println("<td class='tb'>"+rs.getString("category_id")+"</td>");
						out.println("<td class='tb'>"+rs.getString("cast")+"</td>");
						out.println("<td class='tb'>"+rs.getString("date")+"</td>");
						out.println("<td class='tb'><a href='?action=edit_dvd&id="+rs.getString("id")+"'>Edit</a></td>");
						out.println("<td class='tb'><a href='?action=delete_dvd&id="+rs.getString("id")+"'>Delete</a></td>");
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
						else {
							response.sendRedirect("/dvdshop/dvd_list");
						}
					}
				}
			}
			if(session.getAttribute("userrole").equals("customer")) {
				if(request.getParameterMap().containsKey("action")) {
					if(request.getParameter("action").toString().equals("add_dvd_action")) {
						response.setContentType("text/html;charset=UTF-8");
						PrintWriter out = response.getWriter();
						out.println("Access Denied");
					}
					if(request.getParameter("action").toString().equals("search_dvd") && request.getParameterMap().containsKey("name")) {
						if(!request.getParameter("name").toString().equals("")) {
						String name=request.getParameter("name").toString();
						
						
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
					
					
					out.println("<form action='?action=search_dvd' method=\"post\" class=\"tb\">");
					out.println("<p>Search DVD: <input name=\"name\" type=\"text\" /> By ");
					out.println("<select name=\"category\">");
					out.println("<option>title</option>");
					out.println("<option value='category_id'>category</option>");
					out.println("<option>cast</option>");
					out.println("</select></p>");
					
						out.println("<p><input type=\"submit\" value=\"Submit\" /></p>");
						out.println("<p>Search Result for '"+name+"'</p>");
						out.println("</form>");
					
					out.println("<table class='tb'>");
					out.println("<tr><th class='tb'>ID</th><th class='tb'>Title</th><th class='tb'>Category</th><th class='tb'>Actors</th><th class='tb'>Date Added</th></tr>");
					try {
					String sql = "SELECT * FROM dvd WHERE "+request.getParameter("category").toString()+" LIKE'%"+name+"%'";
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
						else {
							response.sendRedirect("/dvdshop/dvd_list");
						}
					}
				}
			}
		}
		else {
			response.sendRedirect("/dvdshop/login");
		}
	}
}