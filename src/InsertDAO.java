import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.log4j.Logger;


public class InsertDAO {
	private Logger log = Logger.getLogger(getClass().getName());
	
	private Connection openConnection() throws Exception {
		Connection connection = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		connection = DriverManager.getConnection(
				(String)MainClass.PROPERTIES.get("DB_URL"), 
				(String)MainClass.PROPERTIES.get("DB_USERNAME"),
				(String)MainClass.PROPERTIES.get("DB_PASSWORD"));
		
		log.info("connected to DB..");
		return connection;
	}
	
	public void insert(List<Integer> list){
		Connection conn = null;
		PreparedStatement pstmt = null;
		int recordInserted = 0;
		try {
			conn = openConnection();
			pstmt = conn.prepareStatement((String)MainClass.PROPERTIES.get("INSERT_QUERY"));
			
			for(Integer i : list){
				pstmt.clearParameters();
				pstmt.setInt(1, i);
				int result = pstmt.executeUpdate();
				
				if(result>0){
					recordInserted++;
				}
			}

			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			
		}finally{
			log.info(recordInserted + " record inserted.");
			try	{
				if (null != pstmt)	{
					pstmt.close();
				}
				if (null != conn)	{
					conn.close();
				}
				log.info("Connection closed.");
				
			} catch (Exception e)	{
				log.error(e.getMessage(), e);
			}
			
		}
	}
	
}
