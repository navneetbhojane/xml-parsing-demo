package com.demo.nav;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ParseXMLUtilDemo {

	private static String searchString = "SEARCH_STRING_1(";
	private static String delimeter = ",";
	private static List<DataRow> dataList = new ArrayList<DataRow>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String inputFile = "src/main/data/input2.txt";
		String outputFile = "src/main/data/output.xlsx";
		
		try {
			
			if(!isValidate(outputFile)) {
				return;
			}
			
			parseInputFile(inputFile);
			generateOutputFile(outputFile);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean isValidate(String outputFile) {
		
		String fileExtn = outputFile.substring(outputFile.indexOf(".")+1);
		//System.out.println(fileExtn);
		
		if(fileExtn.equalsIgnoreCase("xls") || fileExtn.equalsIgnoreCase("xlsx")) {
			return true;
		}
		
		return false;
	}
	
	private static void parseInputFile(String filePath) throws FileNotFoundException {
		
		File inputFile = new File(filePath);
		final Scanner scanner = new Scanner(inputFile);
		while (scanner.hasNextLine()) {
			final String line = scanner.nextLine().trim();
			if (line.contains(searchString)) {
				System.out.println(line);
				parseLine(line);
				//break;
			}
		}
		
	}
	
	private static void parseLine(String line) {
		
		int first = line.indexOf(searchString);
		int second = line.indexOf(")", first + 1);
		
		String[] values = line.substring((first+searchString.length()), second).split(delimeter);
		addDataRow(values[0], values[2], 0);
		
	}
	
	private static void addDataRow(String col_1, String col_3, int count) {
		
		//System.out.println(col_1+","+col_3);
		DataRow dataRow = new DataRow();
		dataRow.setCol_1(col_1);
		dataRow.setCol_3(col_3);
		dataRow.setCount(count);
		dataList.add(dataRow);
	}

	private static void generateOutputFile(String outputFile) throws IOException {
		
		//Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Output Data");
          
        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        int i = 0;
        int j = 0;
        data.put(Integer.toString(++i), new Object[] {"NO", "COL1", "COL2", "COL3", "Count"});
        
        for (Iterator iterator = dataList.iterator(); iterator.hasNext();) {
			DataRow dataRow = (DataRow) iterator.next();
			data.put(Integer.toString(++i), new Object[] {Integer.toString(++j), dataRow.getCol_1(), "NULL", dataRow.getCol_3(), dataRow.getCount()});
			
		}
          
        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }

        //Write the workbook in file system
        FileOutputStream out = new FileOutputStream(new File(outputFile));
        workbook.write(out);
        out.close();
        System.out.println("outputFile written successfully on disk.");
        		
	}

}



class DataRow {
	
	String col_1;
	String col_2;
	String col_3;
	String col_4;
	int count;
	
	public String getCol_1() {
		return col_1;
	}
	public void setCol_1(String col_1) {
		this.col_1 = col_1;
	}
	public String getCol_2() {
		return col_2;
	}
	public void setCol_2(String col_2) {
		this.col_2 = col_2;
	}
	public String getCol_3() {
		return col_3;
	}
	public void setCol_3(String col_3) {
		this.col_3 = col_3;
	}
	public String getCol_4() {
		return col_4;
	}
	public void setCol_4(String col_4) {
		this.col_4 = col_4;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
