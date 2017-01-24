import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
   
public class login extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String msg="";
		if(request.getParameterMap().containsKey("msg")) {
			msg=request.getParameter("msg").toString();
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
        out.println("<title>Login - DVD Shop</title></head>");
		out.println("<style>.tb { border: 1px solid black; padding: 10px; display: inline-block; }</style>");
        out.println("<body>");
		
		out.println("<center>");
		out.println("<h1>Login - DVD Shop</h1>");
		if(msg.equals("wrong")) {
			out.println("<h6>Wrong Username or Password</h6>");
		}
		out.println("<form method=\"post\" class=\"tb\">");
        out.println("<p>Username: <input name=\"username\" type=\"text\" /></p>");
        out.println("<p>Password: <input name=\"password\" type=\"password\" /></p>");
        out.println("<p><input type=\"submit\" value=\"login\" /></p>");
		out.println("</form>");
		out.println("<p><a href=\"create_account\">Create an Account?</a></p>");
		out.println("</center>");
		
        out.println("</body>");
        out.println("</html>");
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String un=request.getParameter("username").toString();
		String pw=request.getParameter("password").toString();
		db data=new db();
		String sql;
		sql = "SELECT * FROM user WHERE username='"+un+"' AND password='"+pw+"'";
		int found=0;
		try {
			ResultSet rs = data.get_data(sql);
			while(rs.next()) {
				found=1;
				HttpSession session=request.getSession();
				session.setAttribute("username", un);
				session.setAttribute("userrole", rs.getString("role"));
				response.sendRedirect("/dvdshop/");
			}
		}
		catch (Exception e) {
			
		}
		if(found==0) {
			response.sendRedirect("/dvdshop/login?msg=wrong");
		}
	}
}