import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
   
public class home extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session=request.getSession();
		if(session.getAttribute("username")!=null) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html><head>");
			out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			out.println("<title>Homepage - DVD Shop</title></head>");
			out.println("<style>.tb { border: 1px solid black; padding: 10px; display: inline-block; }</style>");
			out.println("<body>");
			
			out.println("<center>");
			
			out.println("<h1>DVD Shop - Homepage</h1>");
			
			out.println("<form class=\"tb\">");
			accessControl ac=new accessControl();
			out.println(ac.menu(session.getAttribute("userrole").toString()));
			out.println("</form>");
			
			out.println("</center>");
			
			out.println("</body>");
			out.println("</html>");
		}
		else {
			response.sendRedirect("/dvdshop/login");
		}
	}
}