package build;
/**
 *jdbc链接数据库 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.mysql.cj.x.protobuf.MysqlxSql.StmtExecute;

public class DBUtill implements SqlDao{
	final private String url="jdbc:mysql://localhost:3306/jspdemo?serverTimezone=UTC&useSSL=false";
	final private String user="root";
	final private String pwd="123456";
	final private String driver="com.mysql.cj.jdbc.Driver";
	Connection conn;

	
	public static void main(String[] args) {
		new DBUtill();
	}

	public DBUtill() {
		try {
			//加载类
			Class.forName(driver);
			//链接数据库
			conn=DriverManager.getConnection(url,user,pwd);
			//预处理sql语句
			
			String Sql="insert into logintable values(?, ?),(?, ?)";
			int resultint=this.insertexecute(Sql, new String[] {"361","124","362","124"});
			System.out.println(resultint);
			
////			PreparedStatement stmt=conn.prepareStatement("select * from logintable where username=?;");
////			stmt.setString(1,"356");
//			String Sql = "select * from logintable";
//			Map<String,String> values=new HashMap<>();
//			values.put("username","356");
//			values.put("password","123");
//			ResultSet rs=this.selectexecute(Sql, values);//(Sql, null)
//			while (rs.next()) {
//				System.out.print(rs.getString("username")+" ");
//				System.out.println(rs.getString("password"));
//			}
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public ResultSet selectexecute(String Sql, Map<String, String> values) {
		// TODO Auto-generated method stub 查询
		PreparedStatement stmt;
		try {
			// Sql = 'select * from logintable where username=?;'
			StringBuffer stb=new StringBuffer(Sql);
			if(values !=null) {
				stb.append(" where ");
				boolean flag = true;
				for(Map.Entry<String, String>entry:values.entrySet()) {
					if(flag) {
						flag=false;
						stb.append(entry.getKey()+"='"+entry.getValue()+"' ");
					}
					stb.append("and "+entry.getKey()+"='"+entry.getValue()+"' ");
				}
			}
			
			stmt=conn.prepareStatement(stb.toString());
			return stmt.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int insertexecute(String Sql, String[] arg) {
		// TODO Auto-generated method stub
		PreparedStatement stmt;
		try {
			// Sql = 'insert into logintable values(?, ?)'
			stmt=conn.prepareStatement(Sql);
			for(int i=0;i<arg.length;i++)
				stmt.setString(i+1, arg[i]);
			//修改和插入都可以用update,用户只需要知道能不能成功
			return stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
