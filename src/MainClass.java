import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class MainClass {
	private static Logger log = Logger.getLogger(MainClass.class.getName());
	public static Properties PROPERTIES;

	private static void initLog4jProperties(String log4jFilePath){
		Properties p = new Properties();

		try {
		    p.load(new FileInputStream(log4jFilePath));
		    PropertyConfigurator.configure(p);
		    log.info("Log4j: configured!");
		} catch (Exception e) {
		    //DAMN! I'm not....

		}
	}
	
	private static void initAppProperties(String appPropertyFilePath){
		PROPERTIES = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(appPropertyFilePath);
			PROPERTIES.load(input);

			log.info("Properties loaded successfully !!!");

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	public static void main(String[] args) {
		
		String jarLocation = LocationFinder.getJarLocation(MainClass.class);
		
		String log4jFilePath = jarLocation+File.separator+"resources"+File.separator+"log4j.properties";
		initLog4jProperties(log4jFilePath);
		
		
//		if(args.length !=1){
//			log.error("*******************************");
//			log.error("App property file location: "+jarLocation+File.separator+"resources");
//			log.error("Please provide property file name as an input while running jar....!!!");
//			log.error("*******************************");
//			return;
//		}
//		
		String path="application.properties";
		
//		String appPropertyFilePath = jarLocation+File.separator+"resources"+File.separator+ args[0];
		String appPropertyFilePath = jarLocation+File.separator+"resources"+File.separator+ path;
		
		initAppProperties(appPropertyFilePath);
		
		
//		String csvFile = "F://rbs.csv";
		String csvFile = (String)PROPERTIES.get("CSV_PATH");
		
		if(csvFile == null){
			log.error("*******************************");
			log.error("Could not found CSV_PATH in properties. please configure. SKIPPED rest steps.");
			log.error("*******************************");
			return;
		}
		
		File f = new File(csvFile);
		if(f.exists()){
			if(f.isFile() && (f.getName().contains(".csv") || f.getName().contains("CSV")) ){
				
			}else{
				log.error("*******************************");
				log.error("Not a csv file, please check!!!");
				log.error("*******************************");
				return;
			}
		}else{
			log.error("*******************************");
			log.error("File not found at given perticular location, please check.");
			log.error("*******************************");
			return;
		}
		
    	CSVReader obj = new CSVReader();
    	List<Integer> list = obj.getList(csvFile);

        InsertDAO dao = new InsertDAO();
        //dao.insert(list);
	}

}
