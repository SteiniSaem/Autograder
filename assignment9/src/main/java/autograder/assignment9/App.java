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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;
/**
 * Hello world!
 *
 */
public class App 
{
	public static String[] removeNulls(String[] array) {
		int nullCount = 0;
		for(int i = 0; i < array.length; i++) {
			if(array[i] == null) {
				nullCount++;
			}
		}
		String[] copy = new String[array.length - nullCount];
		for(int i = 0, j = 0; i < array.length; i++) {
			if(array[i] != null) {
				copy[j++] = array[i];
			}
		}
		return copy;
	}
	
	public static void printArray(String[] array) {
		for(int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
	}
	
	public static String getCellData(Workbook wb, int vRow, int vCol)
	{
		Sheet sheet = wb.getSheetAt(0);   //getting the XSSFSheet object at given index  
		Row row = sheet.getRow(vRow); //returns the logical row  
		Cell cell = row.getCell(vCol); //getting the cell representing the given column  
		try {
			if(cell.getStringCellValue().trim().length() != 0) {
				return cell.getStringCellValue().trim();    //getting cell value 
			} else return null;
		}
		catch (NullPointerException e){
			return null;
		}
	}
	
	public static String[][] mapTestCases(Workbook wb)
	{
		Sheet sheet = wb.getSheetAt(0);
		int TCRowNumbers[] = new int[6];
		
		//find row numbers of lines that contain TC1, TC2, TC3, TC4, TC5
		for(Row row: sheet) {
			for(Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					for(int i = 0; i < 6; i++) {
						if (cell.getRichStringCellValue().getString().equals("TC" + (i + 1))) {
		                	TCRowNumbers[i] = row.getRowNum();  
		                }
					}
					if(cell.getRichStringCellValue().getString().equals("4. Which methods do you need to add to the implementation in order to perform your tests in a convenient manner? Suggest method names!")) {
						TCRowNumbers[5] = row.getRowNum()-1;
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
		
		//System.out.println("max lines: " + maxTCLength);
		
		String [][] TCMap = new String[5][maxTCLength];
		for(int i = 0; i < 5; i++) {
			int temp = 0;
			for(int j = TCRowNumbers[i]; j < TCRowNumbers[i+1]; j++) {
				TCMap[i][temp] = getCellData(wb, j, 10);
				temp++;
			}
		}
		for(int i = 0; i < TCMap.length; i++) {
			TCMap[i] = removeNulls(TCMap[i]);
		}
		
		return TCMap;
	}
	
	public static int gradeTCs(String[][] TCMap) {
		/*Pattern newVendingMachine = Pattern.compile("^new VendingMachine[(][1-9]|10[)]$");
		Pattern refill = Pattern.compile("^refill[(][1-9]|10[)]$");*/
		//Matcher matcher = newVendingMachine.matcher("new VendingMachine(4)");
		int correct = 0;
		String[][] correctAnswers = {{"new VendingMachine()", "refill(10)"}, {"new VendingMachine(1)", "coinInserted()", "requestBottle()"}, {"new VendingMachine(10)", "coinInserted()", "requestBottle()"}, {"new VendingMachine(1)", "refill(9)"}};
		for(int i = 0; i < correctAnswers.length; i++) {
			for(int j = 0; j < TCMap.length; j++) {
				/*for(int k = 0; k < TCMap[j].length; k++) {
					System.out.println(TCMap[j][k]);
				}
				for(int k = 0; k < TCMap[j].length; k++) {
					System.out.println(correctAnswers[j][k]);
				}*/
				if(Arrays.equals(correctAnswers[i], TCMap[j])) {
					correct++;
					break;
				}
			}
		}
		return correct;
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
    		String[][] TCQ3 = mapTestCases(wb);
    		//String[][] correctAnswers = {{"new VendingMachine()", "refill(10)"}, {"new VendingMachine(1)", "coinInserted()", "requestBottle()"}, {"new VendingMachine(10)", "coinInserted()", "requestBottle()"}, {"new VendingMachine(1)", "refill(9)"}};
    		//System.out.println(TCQ3[0].length);
    		/*for(int i = 0; i < TCQ3.length; i++) {
    			for(int j = 0; j < TCQ3[i].length; j++) {
    				System.out.println(correctAnswers[i][j] + "\t\t" + TCQ3[i][j]);
    			}
    			System.out.println(Arrays.equals(correctAnswers[i], TCQ3[i]));
    		}*/
    		
    		int grade = gradeTCs(TCQ3);
    		System.out.println(grade);
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
