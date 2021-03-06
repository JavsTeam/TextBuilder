package TextGenerator.parsers;

import TextGenerator.handlers.Writer;
import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

@JBossLog
public class XslxParser {

    public static void parseToTxt(String pathToXslx, String pathToTxt) {
        try {
            log.debug("Trying to parse...");
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
            log.warn(e.getMessage() + "\nError happened in parseToText attempt!");
        }
    }
}
