package configuration.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ReadExcelDataDrivenApproach {
    public static String filePath = "../TDD_WebAutomationFrameworkSingleModule/DataTest/RegistrationDataFromExcel.xlsx";
    public static File file = new File(filePath);
    public static FileInputStream inputStream;
    public static XSSFWorkbook workbook;
    public static XSSFSheet sheet;

    public static Object[][] getRegistrationTestData(String sheetName) {
        try {
            inputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheet(sheetName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //getLastRowNum starts at 0 and displays all the row
        Object[][] registrationData = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
        int row = 0;
        Iterator<Row> rowIterator = sheet.iterator();
        boolean firstRow = true;
        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            if(firstRow){
                firstRow=false;
                continue;
            }
            Iterator<Cell> cellIterator = currentRow.iterator();
            int column = 0;
            while (cellIterator.hasNext()) {
                Cell currentCell = cellIterator.next();
                if (currentCell.getCellType() == CellType.STRING) {
                    registrationData[row][column] = currentCell.getStringCellValue();
                } else if (currentCell.getCellType() == CellType.NUMERIC) {
                    registrationData[row][column] =(int) currentCell.getNumericCellValue();
                }
                column++;
            }
            row++;
        }
        return registrationData;
    }
}
