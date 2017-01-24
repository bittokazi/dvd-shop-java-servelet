import java.io.*;


public class accessControl {
	public String menu(String role) {
		if(role.equals("manager")) {
			String m="<ul style='padding:15px; border:1px solid black;'>";
			m=m+"<a style='text-decoration:none; color:black;' href=\"dvd_category\">DVD Category</a>&nbsp; | &nbsp;";
			m=m+"<a style='text-decoration:none; color:black;' href=\"dvd_category?action=add_dvd\">Add DVD Category</a>&nbsp; | &nbsp;";
			m=m+"<a style='text-decoration:none; color:black;' href=\"dvd_list\">DVD List</a>&nbsp; | &nbsp;";
			m=m+"<a style='text-decoration:none; color:black;' href=\"dvd_list?action=add_dvd\">Add DVD</a>&nbsp; | &nbsp;";
			m=m+"<a style='text-decoration:none; color:black;' href=\"dvd_request\">DVD Request</a>&nbsp; | &nbsp;";
			m=m+"<a style='text-decoration:none; color:black;' href=\"customer_list\">Customer List</a>&nbsp; | &nbsp;";
			m=m+"<a style='text-decoration:none; color:black;' href=\"update_password\">Update Password</a>&nbsp; | &nbsp;";
			m=m+"<a style='text-decoration:none; color:black;' href=\"logout\">Logout</a>&nbsp; | &nbsp;";
			m=m+"</ul>";
			return m;
		}
		else {
			String m="<ul style='padding:15px; border:1px solid black;'>";
			m=m+"<a style='text-decoration:none; color:black;' href=\"dvd_category\">DVD Category</a>&nbsp; | &nbsp;";
			m=m+"<a style='text-decoration:none; color:black;' href=\"dvd_list\">DVD List</a>&nbsp; | &nbsp;";
			m=m+"<a style='text-decoration:none; color:black;' href=\"dvd_request\">DVD Request</a>&nbsp; | &nbsp;";
			m=m+"<a style='text-decoration:none; color:black;' href=\"dvd_request?action=add_dvd_request\">New DVD Request</a>&nbsp; | &nbsp;";
			m=m+"<a style='text-decoration:none; color:black;' href=\"update_password\">Update Password</a>&nbsp; | &nbsp;";
			m=m+"<a style='text-decoration:none; color:black;' href=\"logout\">Logout</a>&nbsp; | &nbsp;";
			m=m+"</ul>";
			return m;
		}
	}
}