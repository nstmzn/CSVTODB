

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class CSVReader {

	Logger log = Logger.getLogger(getClass().getName());
	
    public static void main(String[] args) {
    	String csvFile = "F://rbs.csv";
    	CSVReader obj = new CSVReader();
    	List<Integer> list = obj.getList(csvFile);
    	
    	System.out.println("--------------");
        for(Integer i : list){
        	System.out.println(i);
        }
        
    }
    
    public List<Integer> getList(String filePath){

    	List<Integer> list = new ArrayList<Integer>();
        
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {

                String[] tokensArr = line.split(cvsSplitBy);
                
                String token = tokensArr[0];
                
                if(token != null){
                	if(token.contains("\"")){
                		token = token.replaceAll("\"", "").trim();
                		
                	}else{
                		token = token.trim();
                	}
                }
                
                try{
                	list.add(Integer.parseInt(token));
                	
                }catch(Exception e){
                	e.printStackTrace();
                }
                
            }
            
            

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                	log.error(e.getMessage(), e);
                }
            }
        }
        
        log.info("File path: "+filePath);
        log.info("Number of entries in file: "+list.size());
        
        return list;

    }

}
