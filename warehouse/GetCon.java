package warehouse;

import java.sql.Connection;
import java.sql.DriverManager;

public class GetCon {
	private Connection con=null;
	public Connection GetConnection(){
		 String URL="jdbc:mysql://localhost:3306/warehouse?useUnicode=true&characterEncoding=utf-8&useSSL=false";
		 String USER="root";
		 String KEY="coding";
		 
		 try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(URL, USER, KEY);
			//System.out.println("���ݿ���سɹ�");
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		 return con;
	}
}
