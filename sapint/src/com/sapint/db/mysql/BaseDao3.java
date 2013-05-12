/**
 * 
 */

package com.sapint.db.mysql;

/**
 * @author vincent
 *
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//这里我们建立一个DBHelper类
public class BaseDao3 {
  
   //此方法为获取数据库连接，此处以及后续文章中使用的都是mysql
  public static Connection getCon() {
         Connection con = null;
         try {
              String driver = "com.mysql.jdbc.Driver"; //数据库驱动
              String url = "jdbc:MySQL://127.0.0.1:3306/test";//
              String user = "root"; //用户名
              String password = "root";//密码
              Class.forName(driver); //加载数据库驱动
              if(null == con)
                con = DriverManager.getConnection(url, user, password);
          } 
         catch(ClassNotFoundException e) 
     {   
       System.out.println("Sorry,can't find the Driver!");   
       e.printStackTrace();   
     }
          catch(SQLException e) 
     {   
       e.printStackTrace();   
     } 
         catch (Exception e) {
               e.printStackTrace();
         }
          return con;
     } 

    /**  
     * 查询语句
     *   
     * @param sql  
     */  
     public static ResultSet executeQuery(String sql) throws SQLException {
          Connection con = getCon();
          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(sql);
          return rs;
     }

     public static ResultSet executeQuery(String sql, Object... obj)   throws SQLException {
        Connection con = getCon();
        PreparedStatement pstmt = con.prepareStatement(sql);
        for (int i = 0; i < obj.length; i++) {
              pstmt.setObject(i + 1, obj[i]);
        }
        ResultSet rs = pstmt.executeQuery();
        return rs;
    }
     
     /**  
      * 判断记录是否存在
      *   
      * @param sql  
      */ 
     public static Boolean exist(String sql) throws SQLException{
       
       try{
         ResultSet rs = executeQuery(sql);
         rs.last();
         int count = rs.getRow();
         if(count > 0)
           return true;
         else
           return false;
       }catch(Exception e)
       {
         e.printStackTrace();
         return false;
       }
       
       
     }
     
     /**  
      * 获取查询记录的总行数
      *   
      * @param sql  
      */ 
     public static int count(String sql) throws SQLException{
       ResultSet rs = executeQuery(sql);
       rs.last();
       int count = rs.getRow();
       return count;
     }
     
     /**  
      * 执行增删改
      *   
      * @param sql    
      */  
    public static int executeNonQuery(String sql) throws SQLException {
        Connection con = getCon();
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(sql);
   }

    public static int executeNonQuery(String sql, Object... obj) throws SQLException {
        Connection con = getCon();
        PreparedStatement pstmt = con.prepareStatement(sql);
        for (int i = 0; i < obj.length; i++) {
             pstmt.setObject(i + 1, obj[i]);
        }
       return pstmt.executeUpdate();
    }
    
    /**  
     * 释放数据资源  
     *   
     * @param rs  
     * @param st  
     * @param conn  
     */  
    public static void free(ResultSet rs, Statement st, Connection conn) {   
        try {   
            if (rs != null)   
                rs.close();   
        } catch (SQLException e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (st != null)   
                    st.close();   
            } catch (SQLException e) {   
                e.printStackTrace();   
            } finally {   
                if (conn != null)   
                    try {   
                        conn.close();   
                    } catch (SQLException e) {   
                        e.printStackTrace();   
                    }   
            }   
        }
    }

}

