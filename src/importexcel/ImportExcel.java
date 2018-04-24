package importexcel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 导入excel表格
 *
 * @author xujian
 * @create 2018-04-20 17:25
 **/
public class ImportExcel {
    public static void importExcel(){
        XSSFWorkbook hssfWorkbook = null;
        FileOutputStream outputStream = null;
        try{
            hssfWorkbook = new XSSFWorkbook(new FileInputStream("C:\\Users\\18271\\Downloads\\派学院清洗数据45 .xlsx"));
            XSSFSheet sheet = hssfWorkbook.getSheet("Sheet1");
            File file = new File("C:\\Users\\18271\\Desktop\\新建文本文档.txt");
            if(file.length()>0){
                file.delete();
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            for(int i=1,len=sheet.getLastRowNum();i<=len;i++){
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell5 = row.getCell(5);
                if("费弘韬".equals(cell5.getStringCellValue())){
                    XSSFCell cell = row.getCell(0);
                    String ord_id = cell.getStringCellValue()+",";
                    outputStream.write(ord_id.getBytes());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args){
        importExcel();
    }
}
