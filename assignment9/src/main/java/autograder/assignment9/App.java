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
	public static String getCellData(Workbook wb, int vRow, int vCol)
	{
		Sheet sheet = wb.getSheetAt(0);   //getting the XSSFSheet object at given index  
		Row row = sheet.getRow(vRow); //returns the logical row  
		Cell cell = row.getCell(vCol); //getting the cell representing the given column  
		try {
			return cell.getStringCellValue();    //getting cell value  
		}
		catch (NullPointerException e){
			return "";
		}
	}
	
	public static void mapTestCases(Workbook wb)
	{
		Sheet sheet = wb.getSheetAt(0);
		int TCRowNumbers[] = new int[6];
		
		//find rownumbers of lines that contain TC1, TC2, TC3, TC4, TC5
		for(Row row: sheet) {
			for(Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
	                if (cell.getRichStringCellValue().getString().equals("TC1")) {
	                	TCRowNumbers[0] = row.getRowNum();  
	                }
	                else if (cell.getRichStringCellValue().getString().equals("TC2")) {
	                	TCRowNumbers[1] = row.getRowNum();  
	                }
	                else if (cell.getRichStringCellValue().getString().equals("TC3")) {
	                	TCRowNumbers[2] = row.getRowNum();  
	                }
	                else if (cell.getRichStringCellValue().getString().equals("TC4")) {
	                	TCRowNumbers[3] = row.getRowNum();  
	                }
	                else if (cell.getRichStringCellValue().getString().equals("TC5")) {
	                	TCRowNumbers[4] = row.getRowNum();  
	                }
	                else if (cell.getRichStringCellValue().getString().equals("4. Which methods do you need to add to the implementation in order to perform your tests in a convenient manner? Suggest method names!")) {
	                	TCRowNumbers[5] = row.getRowNum() - 1;  
	                }
	            }
			}
		}
		//find the most amount of rows used in a test case
		int maxTCLength = 0;
		for(int i = 0; i < TCRowNumbers.length-1; i++) {
			if(TCRowNumbers[i+1] - TCRowNumbers[i] > maxTCLength) {
				maxTCLength = TCRowNumbers[i+1] - TCRowNumbers[i];
			}
		}
		
		//System.out.println(maxTCLength);
		
		String [][] TCMap = new String[5][maxTCLength];
		for(int i = 0; i < 5; i++) {
			int temp = 0;
			for(int j = TCRowNumbers[i]; j < TCRowNumbers[i+1]; j++) {
				TCMap[i][temp] = getCellData(wb, j, 10);
				temp++;
			}
		}
		
		for(int i = 0; i < TCMap.length; i++) {
			for(int j = 0; j < TCMap[0].length; j++) {
				System.out.println(TCMap[i][j]);
			}
		}

	}
	
    public static void main( String[] args )
    {
    	Workbook wb = null; 
    	try 
    	{
    		//reading data from a file in the form of bytes  
			FileInputStream fis = new FileInputStream("hbv205m_assignment09.xlsx");  
			//constructs an XSSFWorkbook object, by buffering the whole stream into the memory  
			wb = new XSSFWorkbook(fis);
			
    		//Question 3
    		mapTestCases(wb);
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
