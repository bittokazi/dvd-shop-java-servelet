import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class update_password extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		db data=new db();
		HttpSession session=request.getSession();
		if(session.getAttribute("username")!=null) {
			String msg="";
			if(request.getParameterMap().containsKey("msg")) {
				msg=request.getParameter("msg").toString();
			}
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html><head>");
			out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			out.println("<title>Update Password - DVD Shop</title></head>");
			out.println("<style>.tb { border: 1px solid black; padding: 10px; display: inline-block; }</style>");
			out.println("<body>");
		
			out.println("<center>");
			out.println("<h1>Update Password - DVD Shop</h1>");
			
			out.println("<form class=\"tb\">");
						accessControl ac=new accessControl();
						out.println(ac.menu(session.getAttribute("userrole").toString()));
						out.println("</form>");
			
			out.println("<form style='display: table;'  method=\"post\" class=\"tb\">");
			if(msg.equals("ok")) {
				out.println("<h6>Password Update Successful</h6>");
			}
			if(msg.equals("empty")) {
				out.println("<h6>Password cannot be empty</h6>");
			}
			if(msg.equals("error")) {
				out.println("<h6>Error Occured</h6>");
			}
			out.println("<p>Password: <input name=\"password\" type=\"password\" /></p>");
			out.println("<p><input type=\"submit\" value=\"Update Password\" /></p>");
			out.println("</form>");
			out.println("</center>");
		
			out.println("</body>");
			out.println("</html>");
		}
		else {
			response.sendRedirect("/dvdshop/login");
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		db data=new db();
		HttpSession session=request.getSession();
		if(session.getAttribute("username")!=null) {
			if(!request.getParameter("password").toString().equals("")) {
				String pw=request.getParameter("password").toString();
				String sql;
				sql = "UPDATE user SET password='"+pw+"' WHERE username='"+session.getAttribute("username").toString()+"'";
				if(data.update_data(sql).equals("ok")) {
					response.sendRedirect("/dvdshop/update_password?msg=ok");
				}
				else {
					response.sendRedirect("/dvdshop/update_password?msg=error");
				}
			}
			else {
				response.sendRedirect("/dvdshop/update_password?msg=empty");
			}
		}
		else {
			response.sendRedirect("/dvdshop/login");
		}
	}
}