package TextGenerator.parsers;

import TextGenerator.handlers.Writer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import java.io.FileInputStream;
import java.io.IOException;

public class XslxParser {
    public static void toTxt(String pathToXslx, String pathToTxt) {
        try {
            XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(pathToXslx));
            StringBuilder text = new StringBuilder();

            for (Row row : book.getSheetAt(0)) {
                if (row.getCell(0).getCellType() == CellType.STRING) {
                    text.append(row.getCell(0).getStringCellValue() + "\n");
                } else if (row.getCell(0).getCellType() == CellType.NUMERIC) {
                    text.append(row.getCell(0).getNumericCellValue() + "\n");
                }
            }

            Writer.writeTextTo(text.toString(), pathToTxt);
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}