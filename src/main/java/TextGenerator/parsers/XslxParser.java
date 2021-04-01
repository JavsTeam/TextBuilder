package TextGenerator.parsers;

import TextGenerator.handlers.Writer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.FileInputStream;
import java.io.IOException;

public class XslxParser {
    private static final Logger logger =  LogManager.getLogger(XslxParser.class);
    public static void parseToTxt(String pathToXslx, String pathToTxt) {
        try {
            logger.trace("Trying to parse...");
            XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(pathToXslx));
            StringBuilder text = new StringBuilder();
            for (Row row : book.getSheetAt(0)) {
                if(row.getCell(0) != null) {
                    if (row.getCell(0).getCellType() == CellType.STRING){
                        text.append(row.getCell(0).getStringCellValue()).append("\n");
                    } else if (row.getCell(0).getCellType() == CellType.NUMERIC) {
                        text.append(row.getCell(0).getNumericCellValue()).append("\n");
                    }
                } else {
                    break;
                }
            }

            Writer.writeTextTo(text.toString(), pathToTxt);
            book.close();
        } catch (IOException e) {
            logger.error(e.getMessage() + "\nError happened in parseToText attempt!");
        }
    }
}
