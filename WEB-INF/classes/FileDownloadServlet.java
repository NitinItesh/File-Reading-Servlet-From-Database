import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.sql.*;

@WebServlet("/FileDownloadServlet")
public class FileDownloadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		InputStream filecontent = null;

		try {
			response.setContentType("image/jpeg");
			ServletOutputStream out = response.getOutputStream();

			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/fileupload", "root", "");
			Statement stmt = con.createStatement();
			String sql = "SELECT * FROM uploadfile where fname=\'logo.png\'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
				filecontent = rs.getBinaryStream("filedata");

			BufferedInputStream bin = new BufferedInputStream(filecontent);
			BufferedOutputStream bout = new BufferedOutputStream(out);
			int ch = 0;
			while ((ch = bin.read()) != -1) {
				System.out.println(ch);
				bout.write(ch);
			}
			bin.close();
			bout.close();
			out.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
