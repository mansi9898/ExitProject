package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {
	public static Object[][] getTestData(String filePath, String sheetName) throws IOException, InvalidFormatException {
        FileInputStream inputStream = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getLastRowNum();
        int colCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i + 1); // Skip header row

            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                if (cell != null) {
                	switch (cell.getCellType()) {
                    case STRING:
                        data[i][j] = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        /*if (DateUtil.isCellDateFormatted(cell)) {
                            data[i][j] = cell.getDateCellValue();
                        } else {
                            // Convert numeric value to String
                            data[i][j] = String.valueOf(cell.getNumericCellValue());
                        }*/
                    	// Treat numeric value as string
                        cell.setCellType(CellType.STRING);
                        data[i][j] = cell.getStringCellValue();
                        break;
                    case BOOLEAN:
                        data[i][j] = cell.getBooleanCellValue();
                        break;
                    case BLANK:
                        data[i][j] = "";
                        break;
                    default:
                        data[i][j] = "";
                        break;
                    }
                } else {
                    data[i][j] = "";
                }
            }
        }

        workbook.close();
        inputStream.close();

        return data;
    }
}
