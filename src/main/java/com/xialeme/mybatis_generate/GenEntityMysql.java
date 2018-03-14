package com.xialeme.mybatis_generate;

import java.io.BufferedWriter;
import java.io.File;  
import java.io.FileWriter;  
import java.io.IOException;  
import java.io.PrintWriter;  
import java.io.Serializable;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;  
import java.sql.SQLException;  
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;  
import java.util.List;
  
public class GenEntityMysql {  
      
	private String projectAbsolutePath = "d:/new_project/src/main/java";//指定项目所在包的src绝对路径
    private String packageOutPath = "com.xialeme.entity";//指定实体生成所在包的路径  
    private String authorName = "bamboo zjcjava@163.com";//作者名字  
    private String tablename = "xm_dealer";//表名  
    private List<String> colnames=new ArrayList<String>(); // 列名数组  
    private List<String> colTypes=new ArrayList<String>(); //列名类型数组  
    private List<String> colComments=new ArrayList<String>(); //列名注释数组
    private int[] colSizes; //列名大小数组  
    private boolean f_util = false; // 是否需要导入包java.util.*  
    private boolean f_sql = false; // 是否需要导入包java.sql.*  
      
    //数据库连接  
    private static final String URL ="jdbc:mysql://118.190.116.10:3306/xialeme";  
    private static final String NAME = "root";  
    private static final String PASS = "root";  
    private static final String DRIVER ="com.mysql.jdbc.Driver";  
  
    /* 
     * 构造函数 
     */  
    public GenEntityMysql(){  
        //创建连接  
        Connection con;  
        //查要生成实体类的表  
        String sql = "select * from " + tablename;  
        PreparedStatement pStemt = null;  
        try {  
            try {  
                Class.forName(DRIVER);  
            } catch (ClassNotFoundException e1) {  
                // TODO Auto-generated catch block  
                e1.printStackTrace();  
            }  
            con = DriverManager.getConnection(URL,NAME,PASS);  
           /* pStemt = con.prepareStatement(sql);  
            ResultSetMetaData rsmd = pStemt.getMetaData();  
            int size = rsmd.getColumnCount();   //统计列  
//            colnames = new String[size];  
//            colTypes = new String[size];  
//            colSizes = new int[size];  
            for (int i = 0; i < size; i++) {  
                colnames.get(i) = rsmd.getColumnName(i + 1);  
                colTypes.get(i) = rsmd.getColumnTypeName(i + 1);  
                System.out.println(rsmd.getColumnTypeName(i + 1));
                if(colTypes.get(i).equalsIgnoreCase("datetime")){  
                    f_util = true;  
                }  
                if(colTypes.get(i).equalsIgnoreCase("image") || colTypes.get(i).equalsIgnoreCase("text")){  
                    f_sql = true;  
                }  
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);  
                System.out.println(rsmd.getColumnClassName(i + 1));
            }  
            */
            
            
            Statement stmt = con.createStatement();  
            ResultSet rs = stmt.executeQuery("show full columns from " + tablename);  
            System.out.println("【"+tablename+"】");  
//          if (rs != null && rs.next()) {  
                //map.put(rs.getString("Field"), rs.getString("Comment")); 
            while (rs.next()) {    
            	colnames.add(underlineToCamel(rs.getString("Field")));
            	colTypes .add(rs.getString("Type").split("\\(")[0]);
            	colComments.add( rs.getString("Comment"));
//              System.out.println("字段名称：" + rs.getString("Field") + "\t"+ "字段注释：" + rs.getString("Comment") );  
                System.out.println(rs.getString("Field") + "\t"+  rs.getString("Type").split("\\(")[0]+ "\t"+  rs.getString("Comment") );  
            }   
//          }  
            rs.close();  
              
            String content = parse(colnames,colTypes);  
              
            try {  
//                File directory = new File("");  
//                String path=this.getClass().getResource("").getPath();  
//                  
//                System.out.println(path);  
//                System.out.println("src/?/"+path.substring(path.lastIndexOf("/com/", path.length())) );  
//                String outputPath = directory.getAbsolutePath()+ "/src/"+this.packageOutPath.replace(".", "/")+"/"+initcap(tablename) + ".java";  
            	String currentFileDir=projectAbsolutePath.endsWith("/")?projectAbsolutePath:(projectAbsolutePath+"/")+packageOutPath.replace(".", "/")+"/";
            	File fileDir = new File(currentFileDir);
        		if (!fileDir.exists()) {
        			fileDir.mkdirs();
        		}
        		String outputFilePath =currentFileDir+initcap(underlineToCamel(tablename)) + ".java";  
        		System.out.println(outputFilePath);
        		//System.out.println(content);
        		File f = new File(outputFilePath);  
                if (f.exists()) {  
                    System.out.print("文件存在");  
                } else {  
                    System.out.print("文件不存在");  
                    f.createNewFile();// 不存在则创建  
                    System.out.print("文件创建成功"); 
                } 
        		BufferedWriter output = new BufferedWriter(new FileWriter(f));  
                output.write(content);  
                output.close(); 
            	/*FileWriter fw = new FileWriter(outputFilePath);  
                PrintWriter pw = new PrintWriter(fw);  
                pw.println(content);  
                pw.flush();  
                pw.close(); */ 
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
              
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally{  
//          try {  
//              con.close();  
//          } catch (SQLException e) {  
//              // TODO Auto-generated catch block  
//              e.printStackTrace();  
//          }  
        }  
    }  
  
    /** 
     * 功能：生成实体类主体代码 
     * @param colnames 
     * @param colTypes 
     * @param colSizes 
     * @return 
     */  
    private String parse(List colnames, List colTypes) {  
        StringBuffer sb = new StringBuffer();  
        sb.append("package " + this.packageOutPath + ";\r\n");  
        //判断是否导入工具包  
        if(f_util){  
            sb.append("import java.util.Date;\r\n");  
        }  
        if(f_sql){  
            sb.append("import java.sql.*;\r\n");  
        }  
        sb.append("import java.io.Serializable;");  
        sb.append("\r\n");  
        //注释部分  
        sb.append("   /**\r\n");  
        sb.append("    * "+tablename+" 实体类\r\n");  
        sb.append("    * "+new Date()+" "+this.authorName+"\r\n");  
        sb.append("    */ \r\n");  
        //实体部分  
        sb.append("public class " + initcap(underlineToCamel(tablename)) + "  implements Serializable {\r\n");  
        sb.append("\t /** serialVersionUID. */\r\n \t private static final long serialVersionUID ="+System.currentTimeMillis()+"L;\r\n");  
        processAllAttrs(sb);//属性  
        processAllMethod(sb);//get set 方法  
        sb.append("}\r\n");  
          
        //System.out.println(sb.toString());  
        return sb.toString();  
    }  
      
    /** 
     * 功能：生成所有属性 
     * @param sb 
     */  
    private void processAllAttrs(StringBuffer sb) {  
          
        for (int i = 0; i < colnames.size(); i++) {  
            sb.append("\tprivate " + sqlType2JavaType(colTypes.get(i)) + " " + colnames.get(i) + ";//"+colComments.get(i)+"\r\n");  
        }  
          
    }  
  
    /** 
     * 功能：生成所有方法 
     * @param sb 
     */  
    private void processAllMethod(StringBuffer sb) {  
          
        for (int i = 0; i < colnames.size(); i++) {  
            sb.append("\tpublic void set" + initcap(colnames.get(i)) + "(" + sqlType2JavaType(colTypes.get(i)) + " " +   
                    colnames.get(i) + "){\r\n");  
            sb.append("\tthis." + colnames.get(i) + "=" + colnames.get(i) + ";\r\n");  
            sb.append("\t}\r\n");  
            sb.append("\tpublic " + sqlType2JavaType(colTypes.get(i)) + " get" + initcap(colnames.get(i)) + "(){\r\n");  
            sb.append("\t\treturn " + colnames.get(i) + ";\r\n");  
            sb.append("\t}\r\n");  
        }  
         //Tostring
       
        for (int i = 0; i < colnames.size(); i++) {  
        	if(i==0){
        		 sb.append("\t@Override\r\n\tpublic String toString (){\r\n");  
        	        sb.append("\treturn \"" +initcap(underlineToCamel(tablename))+"["+colnames.get(i)+"=\"+"+colnames.get(i));
        	}else if(i==(colnames.size()-1)){
        		 sb.append(  "+\"," + colnames.get(i) +"=\"+" + colnames.get(i) );  
        		 sb.append("+\"]\";\r\n");  
     	        sb.append("\t}\r\n"); 
        	}else{
        		sb.append("+\", " +colnames.get(i)+"=\"+"+colnames.get(i));
        		
        	}
           
        }
       
    }  
      
    /** 
     * 功能：将输入字符串的首字母改成大写 
     * @param str 
     * @return 
     */  
    private String initcap(String str) {  
          
        char[] ch = str.toCharArray();  
        if(ch[0] >= 'a' && ch[0] <= 'z'){  
            ch[0] = (char)(ch[0] - 32);  
        }  
          
        return new String(ch);  
    }  
    
    /** 
     * 功能：下横线转驼峰
     * @param str 
     * @return 
     */  
    public static String underlineToCamel(String param){  
        if (param==null||"".equals(param.trim())){  
            return "";  
        }  
        int len=param.length();  
        StringBuilder sb=new StringBuilder(len);  
        for (int i = 0; i < len; i++) {  
            char c=param.charAt(i);  
            if (c=='_'){  
               if (++i<len){  
                   sb.append(Character.toUpperCase(param.charAt(i)));  
               }  
            }else{  
                sb.append(c);  
            }  
        }  
        return sb.toString();  
    } 
  
    /** 
     * 功能：获得列的数据类型 
     * @param sqlType 
     * @return 
     */  
    private String sqlType2JavaType(String sqlType) {  
          
        if(sqlType.equalsIgnoreCase("bit")){  
            return "boolean";  
        }else if(sqlType.equalsIgnoreCase("tinyint")){  
            return "byte";  
        }else if(sqlType.equalsIgnoreCase("smallint")){  
            return "short";  
        }else if(sqlType.equalsIgnoreCase("int")){  
            return "int";  
        }else if(sqlType.equalsIgnoreCase("bigint")){  
            return "long";  
        }else if(sqlType.equalsIgnoreCase("float")){  
            return "float";  
        }else if(sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")   
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")   
                || sqlType.equalsIgnoreCase("smallmoney")|| sqlType.equalsIgnoreCase("double")){  
            return "double";  
        }else if(sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")   
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")   
                || sqlType.equalsIgnoreCase("text")){  
            return "String";  
        }else if(sqlType.equalsIgnoreCase("datetime")){  
            return "Date";  
        }else if(sqlType.equalsIgnoreCase("image")){  
            return "Blod";  
        }  
          
        return null;  
    }  
      
    /** 
     * 出口 
     * TODO 
     * @param args 
     */  
    public static void main(String[] args) {  
          
        new GenEntityMysql();  
    	/*String projectAbsolutePath = "d:/new_project/src/main/java";//指定项目所在包的src绝对路径
    	File fileDir = new File(projectAbsolutePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}*/
    }  
  
}
