package cn.freeliver.library;
import java.sql.SQLException;//SQL异常
import java.sql.Connection;//连接对象
import java.sql.DriverManager;//驱动管理器
import java.sql.Statement;//SQL语句
import java.sql.ResultSet;//数据集
import java.sql.PreparedStatement;//预处理sql语句对象

/**
*@author freeliver
*@email freeliver204@gmail.com
*@version 0.1
*@package cn.freeliver.library
*@lastModified 2010/4/11 23:50
*/
public class MysqlDriver {

    /**
    *@var _dsn  data sources name
    */

    public final static String _dsn="jdbc:mysql://localhost/rms";

    /**
    *@var _userName access server user
    */

    public final static String _userName="root";

    /**
    *@var _password access server password
    */

    public final static String _password="a1a1a1";

    /**
    *@var connection Connection Object
    */

    private Connection connection=null;

    /**
    *@var stmt Statement Object
    */
    private Statement stmt=null;

    /**
    *@var ppStatement PreparedStatement
    */

    private PreparedStatement ppStatement=null;

    /**
    *@var rs ResultSet Object
    */

    private ResultSet rs=null;

    /**
    *@var object MysqlDriver Object
    */

    private static MysqlDriver object=null;

    /**
    *singleton pattern
    *@access private
    */

    private MysqlDriver(){
        this.buildConnect();
    }

    //测试main方法
    public static void main(String[] args) {
        MysqlDriver t1=MysqlDriver.getInstance();

        try{
            System.out.println("查看一个测试的数据表数据：");
            ResultSet rs=t1.query("select*from rms_users");
            while(rs.next()){
                System.out.println(rs.getString("userName"));
            }
        }catch(SQLException e){
        }finally{
            t1.close();
        }
    }

    /**
    *buildConnect() 连接到数据库的连接
    *@return void
    */

    public void buildConnect(){
        try{
            Class.forName("org.gjt.mm.mysql.Driver");//使用mysql Driver 其实现了自动注册这个驱动的对象
            connection=DriverManager.getConnection(_dsn,_userName,_password);
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
    *@return ResultSet 结果集对象
    *@access public
    *@param String sql 查询的sql语句
    *@throw SQLException
    */

    public ResultSet query(String sql) throws SQLException{
        if(this.connection==null) this.buildConnect();

        try{
            stmt=connection.createStatement();
            rs=stmt.executeQuery(sql);
        }catch(SQLException e){
            throw e;
        }
        return rs;
    }

    public PreparedStatement getPPStatement(String sql) throws SQLException{
        if(this.connection==null) this.buildConnect();
        try{
            ppStatement=connection.prepareStatement(sql);
        }catch(SQLException e){
            throw e;
        }
        return ppStatement;
    }

    /**
    *close() 关闭当前的连接等资源
    *@return void
    */

    public void close(){
        try{
            //关闭ResultSet 数据集
            if(rs!=null){ rs.close(); rs=null; }
            //关闭Statement 语句对象
            if(stmt!=null){ stmt.close(); stmt=null; }
            //关闭Connection 连接对象
            if(connection!=null){  connection.close();  connection=null; }
        }catch(SQLException e){
        }
    }

    /**
    *getInstance() 获取MysqlDriver 实例，用于单例模式
    *@access public
    *@return MysqlDriver
    */

    public static MysqlDriver getInstance(){
        if(MysqlDriver.object==null){
            MysqlDriver.object=new MysqlDriver();
        }
        return MysqlDriver.object;
    }

}
