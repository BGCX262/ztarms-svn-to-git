package cn.freeliver;
import java.sql.*;
import cn.freeliver.library.MysqlDriver;
import static cn.freeliver.util.U.*;
import java.util.*;

/**
*LoginService 用于RPC的远程登陆服务类
*@author freeliver
*@email freeliver<freeliver204@gmail.com>
*@version 0.1
*@package cn.freeliver
*@lastModified  2010/4/11 23:37
*/

public class LoginService {

    private String userName="";
    private String password="";
    private MysqlDriver mysqlDriver=null;

    public LoginService(){
         mysqlDriver=MysqlDriver.getInstance();
    }

    public String[] getLoginConfig(){
        return new String[]{"1","2","3"};
    }

    public static void main(String[] args){
        LoginService a=new LoginService();
        String[] result=a.login("admin2010","admin2010");
        if(new Integer(result[0])==0){
            System.out.println("登陆状态码:"+result[0]+";信息："+result[1]);
        }else{
            System.out.println("登陆状态码:"+result[1]+";登陆ID："+result[1]+";登陆类型："+result[2]+";权限内容："+result[3]+";角色名称："+result[4]);
        }
        //System.out.println("regStatus:"+a.register("aaaa","cccc","freeliver204@gmail.com",Math.round(Math.random()*10)));
    }

    /**
    *login() 登陆服务端方法
    *@return String[] 登陆信息行
    *@access public
    *@param String userName 用户账户
    *@param String password 登陆密码
    */

    public String[] login(String userName,String password){
        this.userName=userName;
        this.password=password;

        String modules="none";
        String roleName="custom";
        String[] authention=new String[]{""};
        String sql="select id,userName,password,type,isCheck ,guestTimes,FROM_UNIXTIME(cTime,'%Y/%m/%d %H:%i:%s ') as lastGuestTime,lastGuestIP from rms_users where userName='"+userName+"' AND isCheck=1 LIMIT 1";
        StringBuffer getRolesSql=new StringBuffer("select roles.id as roleID,roles.modules as modules ,roles.name as roleName ");
        getRolesSql.append(" from rms_roles as roles left join rms_users as users on roles.id=users.roleID AND users.type=2");

        /* users.type= 1 管理员 2 一般用户*/
        try{
            ResultSet rs=mysqlDriver.query(sql);
            if(rs.next()){
                if(password.equals(rs.getString("password"))==false){//TODO md5(password)
                    authention=new String[]{"1","密码不匹配，请重新输入"};
                }else{
                    //登陆成功之后一般用户则获取用户的角色所有权限
                    if(rs.getInt("type")==2){
                        ResultSet roleRS=mysqlDriver.query(getRolesSql.toString());
                        if(roleRS.next()){
                            modules=roleRS.getString("modules");
                            roleName=roleRS.getString("roleName");
                        }
                    }else if(rs.getInt("type")==1) {
                        modules="all";
                        roleName="超级管理员";
                    }else{}//nothing to do
                    authention=new String[]{"2",rs.getString("id"),rs.getString("type"),modules,roleName,rs.getString("guestTimes"),rs.getString("lastGuestIP"),rs.getString("lastGuestTime"),dateFormat(getUTCTime())};
                }
            }else{
                authention= new String[]{"0","账户不存在或者被锁定，请联系管理员"};
            }
        }catch(SQLException e){
            authention= new String[]{"0",e.getMessage()};
        }catch(Exception e){
            authention= new String[]{"0",e.getMessage()};
        }finally{
            mysqlDriver.close();//TODO 这里将connection对象关闭之后connection已经不存在，导致单例模式设计失败，再次请求时mysqlDriver对象中已经不存在connection对象导致失败！
        }
        return authention;

    }

    public String[] register(String userName,String password,String email){
        return this.register(userName,password,email,0);
    }

    /**
    *register() 远程注册方法
    *@return String[] 注册是否成功
    *@param String userName 用户账户
    *@param String password 登录密码
    *@param String email 注册的email地址
    */

    public String[] register(String userName,String password,String email,int isLock){
        String[] isReg=new String[]{""};
        if(userName.equals("")) isReg=new String[]{"0","注册登录账户不能为空"};
        else if(password.equals("")) isReg=new String[]{"0","注册登录密码不能为空"};
        else if(email.equals("")) isReg=new String[]{"0","注册E-mail地址不能为空"};
        long cTime=getUTCTime();
        String checkSql="select id from rms_users where username=\""+userName+"\" or email=\""+email+"\"";
        String sql="insert into rms_users (username,password,email,isCheck,cTime)values(?,?,?,?,?)";
        PreparedStatement ppStmt=null;
        try{

            //检查用户名或者E-mail是否已经存在
            ResultSet rs=mysqlDriver.query(checkSql);
            if(rs.next()){
                isReg=new String[]{"0","登录账户或者E-mail地址已经存在"};
            }else{
                //不重复则加入这个注册用户
                ppStmt=mysqlDriver.getPPStatement(sql);
                ppStmt.setString(1,userName);
                ppStmt.setString(2,password);
                ppStmt.setString(3,email);
                ppStmt.setInt(4,isLock);//默认为不通过
                ppStmt.setLong(5,cTime);
                if(0==ppStmt.executeUpdate()){
                    isReg=new String[]{"0","注册失败，请联系管理员"};
                }else{
                    isReg=new String[]{"1","您已经成功注册"};
                }
            }
        }catch(SQLException e){
                    isReg=new String[]{"0",e.getMessage()};
        }finally{
            mysqlDriver.close();
        }
        return isReg;
    }

}//end class