package com.xialeme.utlis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.ss.usermodel.WorkbookFactory;  
import org.apache.poi.ss.usermodel.DateUtil; 

/**   
 * @Title: EexcelTool.java 
 * @Package com.xialeme.utlis 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author bamboo  <a href="zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题：">Bamboo</a>   
 * @date 2017年6月2日 下午4:49:33 
 * @version V1.0   
 */
public class EexcelTool {
	
	
	static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	static DecimalFormat df = new DecimalFormat("#");
    static Pattern pattern = Pattern.compile("^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$"); 

	 /**
     * 读取Excel测试，兼容 Excel 2003/2007/2010
	 * @return 
     */
    public static Map<String, String> readExcel(FileInputStream is)
    {
    	Map<String, String>  tmpMap=new HashMap<>();
        try {
            //同时支持Excel 2003、2007
//            File excelFile = new File("D:/tep/2017-06-03.xls"); //创建文件对象
//            FileInputStream is = new FileInputStream(excelFile); //文件流
            Workbook workbook = WorkbookFactory.create(is); //这种方式 Excel 2003/2007/2010 都是可以处理的
            int sheetCount = workbook.getNumberOfSheets();  //Sheet的数量
            //遍历每个Sheet
            for (int s = 0; s < sheetCount; s++) {
                Sheet sheet = workbook.getSheetAt(s);
                int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数
                //遍历每一行
                for (int r = 0; r < rowCount; r++) {
                    Row row = sheet.getRow(r);
                    int cellCount = row.getPhysicalNumberOfCells(); //获取总列数
                    String phone =getCellVlue(row.getCell(0)) ;
                    String name = getCellVlue(row.getCell(1));
                    tmpMap.put(phone, name);
//                  System.out.println(getCellVlue(cell));
                    //遍历每一列
//                    for (int c = 0; c < cellCount; c++) {
//                        Cell cell = row.getCell(c);
//                        System.out.println(getCellVlue(cell));
//                    }
//                    System.out.println();
                }
            }
            
            

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return tmpMap;
    }
	
    public static String getCellVlue (Cell cell){
    	
    	if(cell==null){
    		return null;
    	}
    	 int cellType = cell.getCellType();
         String cellValue = null;
         switch(cellType) {
             case Cell.CELL_TYPE_STRING: //文本
                 cellValue = cell.getRichStringCellValue().getString();
                 if(pattern.matcher(cellValue).matches()){//科学计数
                 	cellValue=df.format(Double.parseDouble(cellValue));
                 	System.out.println("---");
                 }
                 //System.out.println("type666=="+df.format(cell.getNumericCellValue()));
                 
                 
                 break;
             case Cell.CELL_TYPE_NUMERIC: //数字、日期
                 if(DateUtil.isCellDateFormatted(cell)) {
                     cellValue = fmt.format(cell.getDateCellValue()); //日期型
                    
                 } else {
                     cellValue = String.valueOf(cell.getNumericCellValue()); //数字
                     if(pattern.matcher(cellValue).matches()){//科学计数
                     	cellValue=df.format(Double.parseDouble(cellValue));
                     }
                 }
                 break;
             case Cell.CELL_TYPE_BOOLEAN: //布尔型
                 cellValue = String.valueOf(cell.getBooleanCellValue());
                 break;
             case Cell.CELL_TYPE_BLANK: //空白
                 cellValue = cell.getStringCellValue();
                 break;
             case Cell.CELL_TYPE_ERROR: //错误
                 cellValue = "错误";
                 break;
             case Cell.CELL_TYPE_FORMULA: //公式
                 cellValue = "错误";
                 break;
             default:
                 cellValue = "错误";
         }
         //System.out.print(cellValue + "    ");
         
         return cellValue;
    }
    public static void main (String []args) throws FileNotFoundException{
    	File excelFile = new File("D:/tep/2017-06-03.xls"); //创建文件对象
    	FileInputStream is = new FileInputStream(excelFile); //文件流
    	readExcel(is);
//    	Pattern pattern = Pattern.compile("^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$"); 
//    	DecimalFormat df = new DecimalFormat("#");
//        if(pattern.matcher("1.396223662E10").matches()){
//        	System.out.println(df.format(Double.parseDouble("1.396223662E10")));
//        }
    }
	
}

