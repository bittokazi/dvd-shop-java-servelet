import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
   
public class create_account extends HttpServlet {
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
        out.println("<title>Registration - DVD Shop</title></head>");
		out.println("<style>.tb { border: 1px solid black; padding: 10px; display: inline-block; }</style>");
        out.println("<body>");
		
		out.println("<center>");
		out.println("<h1>Create Account - DVD Shop</h1>");
		if(msg.equals("wrong")) {
			out.println("<h6>Wrong Username or Password</h6>");
		}
		out.println("<form method=\"post\" class=\"tb\">");
        out.println("<p>Username: <input name=\"username\" type=\"text\" /></p>");
        out.println("<p>Password: <input name=\"password\" type=\"password\" /></p>");
		out.println("<p>Repeat Password: <input name=\"password1\" type=\"password\" /></p>");
		out.println("<p>Name: <input name=\"name\" type=\"text\" /></p>");
		out.println("<p>DOB: <input name=\"dob\" type=\"text\" /></p>");
		out.println("<p>Email: <input name=\"email\" type=\"text\" /></p>");
		out.println("<p>Cell: <input name=\"cell\" type=\"text\" /></p>");
		out.println("<p>Gender: <input type='radio' name='gender' value='male' checked> Male");
		out.println("<input type='radio' name='gender' value='female'> Female");
		out.println("<input type='radio' name='gender' value='other'> Other</p>");
		out.println("<p><input type=\"submit\" value=\"Create Account\" /></p>");
        out.println("</form>");
		out.println("</center>");
		
        out.println("</body>");
        out.println("</html>");
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String un=request.getParameter("username").toString();
		String pw=request.getParameter("password").toString();
		String pw1=request.getParameter("password1").toString();
		String name=request.getParameter("name").toString();
		String dob=request.getParameter("dob").toString();
		String email=request.getParameter("email").toString();
		String cell=request.getParameter("cell").toString();
		String gender=request.getParameter("gender").toString();
		db data=new db();
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
        out.println("<title>Registration - DVD Shop</title></head>");
		out.println("<style>.tb { border: 1px solid black; padding: 10px; display: inline-block; }</style>");
        out.println("<body>");
		out.println("<center>");
		if(un.equals("") || pw.equals("") || pw1.equals("") || name.equals("") || dob.equals("") || email.equals("") || cell.equals("")) {
			out.println("<h3>Empty Fields</h3>");
		}
		else {
			if(!pw.equals(pw1)) {
				out.println("<h3>Passwords Do not Match</h3>");
			}
			else {
				String sql;
				sql = "SELECT * FROM user WHERE username='"+un+"'";
				int found=0;
				try {
					ResultSet rs = data.get_data(sql);
					while(rs.next()) {
						found=1;
					}
				}
				catch (Exception e) {
			
				}
				if(found==1) {
					out.println("<h3>Username Already Exist</h3>");
				}
				else {
					sql = "SELECT * FROM user WHERE email='"+email+"'";
					found=0;
					try {
						ResultSet rs = data.get_data(sql);
						while(rs.next()) {
							found=1;
						}
					}
					catch (Exception e) {
			
					}
					if(found==1) {
						out.println("<h3>Email Already Exist</h3>");
					}
					else {
						sql="INSERT INTO user VALUES('0','"+un+"','"+pw+"','"+name+"','"+dob+"','"+cell+"','"+email+"','customer','','"+gender+"')";
						if(data.insert_data(sql).equals("ok")) {
							out.println("<h3>SuccessFully Created Your Account</h3>");
							out.println("<p><a href='login'>LOGIN HERE</a></p>");
						}
						else {
							out.println("<h3>Error Occured. Please Try Again</h3>");
						}
					}
				}
			}
		}
		out.println("</center>");
        out.println("</body>");
        out.println("</html>");
	}
}