package autograder.assignment9;

import java.io.File;  
import java.io.FileInputStream; 
import java.io.FileNotFoundException;  
import java.util.Iterator;  
import java.io.IOException;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;
/**
 * Hello world!
 *
 */
public class App 
{
	public static String getCellData(FileInputStream fis, Workbook wb, int vRow, int vCol)
	{
		String value = null;          //variable for storing the cell value  
		Sheet sheet=wb.getSheetAt(0);   //getting the XSSFSheet object at given index  
		Row row = sheet.getRow(vRow); //returns the logical row  
		Cell cell = row.getCell(vCol); //getting the cell representing the given column  
		try {
			return cell.getStringCellValue();    //getting cell value  
		}
		catch (NullPointerException e){
			return "";
		}
	}
	
    public static void main( String[] args )
    {
    	Workbook wb = null; 
    	try 
    	{
    		//reading data from a file in the form of bytes  
			FileInputStream fis = new FileInputStream("/Users/Steini/Desktop/HÍ_vor_2021/Prófun hugbúnaðar/assignment09.xlsx");  
			//constructs an XSSFWorkbook object, by buffering the whole stream into the memory  
			wb = new XSSFWorkbook(fis);
			
    		//Question 3
    		for(int i = 30; i <= 49; i++) {
    			System.out.println(getCellData(fis, wb, i, 10));
    		}
    	}
    	
    	catch(FileNotFoundException e)
    	{  
    		e.printStackTrace();  
    	}
    	catch(IOException e1)
    	{
    		e1.printStackTrace();
    	}
    }
}
