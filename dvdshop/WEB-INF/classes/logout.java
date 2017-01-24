import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
   
public class logout extends HttpServlet {


   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException {
      PrintWriter out = response.getWriter();
  
		 HttpSession session=request.getSession();
		 if(session.getAttribute("username")!=null) {
        
		session.removeAttribute("userrole");
		session.removeAttribute("username");
    response.sendRedirect("/dvdshop/login");
    return;
		
		 }
		 else {
		response.sendRedirect("/dvdshop/login");
		 }
   }
}