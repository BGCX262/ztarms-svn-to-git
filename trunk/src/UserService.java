package cn.freeliver;
import cn.freeliver.library.MysqlDriver;
import java.sql.*;
import java.util.*;
import static cn.freeliver.util.U.*;



/**
*@author freeliver
*@email freeliver<freeliver204@gmail.com>
*@version 0.1
*@package cn.freeliver
*@lastModifiedDate 2010-4-20 21:01
*/

public class UserService  {

    private MysqlDriver mysqlDriver=null;
    public UserService(){
        mysqlDriver=MysqlDriver.getInstance();
    }
    public static void main(String[] args) {
        UserService a=new UserService();
        System.out.println(a.getUserList(1));
    }

    /**
    *register() 注册一个新用户
    *@param String userName 用户账户
    *@param String password 密码
    *@param String email E-mail地址
    *@param String int isLock 0表示锁定 1表示不锁定
    *@return String[]
    */

    public String[] register(String userName,String password,String email,int isLock){
       cn.freeliver.LoginService loginService=new cn.freeliver.LoginService();
       return loginService.register(userName,password,email,isLock);
    }

    /**
    *addDetailInfo() 增加详细信息
    */

    public String[] addDetailInfo(){
        return new String[]{""};
    }

    public ArrayList getUserList(int p){
        int page=p<1?1:p;
        //所有记录数
        Double totalCount=0.0;
        //分页每页的记录数
        Double listCount=10.0;
        //总共的页数
        int totalPage=0;
        ArrayList<String[]>resultSet=new ArrayList<String[]>();
        try{
            String countSql="select count(id) as totalCount from rms_users";
            ResultSet rs=mysqlDriver.query(countSql);
            if(rs.next()){
                totalCount=rs.getDouble("totalCount");
            }else{
                throw new SQLException("query error");
            }
            totalPage=(int)Math.ceil(totalCount/listCount);//得到总页数
            page=p>totalPage?totalPage:p;
            double start=(p-1)*listCount;
            double end=listCount;
            String getUsrListSql="select*from rms_users order by cTime Desc limit "+(int)start+","+(int)end;
            rs=mysqlDriver.query(getUsrListSql);
            //放置在前面，在删除的时候将导致效率比较低
            resultSet.add(new String[]{new Integer(listCount.intValue()).toString(),new Integer(totalCount.intValue()).toString(),new Integer(totalPage).toString()});
            while(rs.next()){
                //0 分页记录数   1 记录总数   2 总页数  3 ID  4 username 5 isCheck 6  type 7 password
                String type=rs.getInt("type")==1?"管理员":"一般用户";
                String isCheck=rs.getInt("isCheck")==0?"锁定":"解锁";
                String cTime = dateFormat(rs.getLong("cTime"));
                int length=rs.getString("password").length();
                String pw=mkPassword(length);
                resultSet.add(new String[]{rs.getString("id"),rs.getString("username"),pw,type,cTime,rs.getString("lastGuestIP"),"点击查看",isCheck});
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            mysqlDriver.close();
        }
        return resultSet;
    }

    /**
    *deleteById() 根据ID值删除一条记录
    *@ return boolean 是否删除成功
    *@ param int id ID值
    */

    public boolean deleteById(int id){
        if(id==0) return false;
        try{
            PreparedStatement ppStatement=mysqlDriver.getPPStatement("delete from rm_users where id=? limit 1");
            ppStatement.setInt(1,id);
            if(0==ppStatement.executeUpdate()){
                return false;
            }else{
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            mysqlDriver.close();
        }
        return true;
    }

    /**
    *changType() 修改用户的类型
    *@param int type 类型
    *@param int id 用户的id
    *@return boolean 是否修改成功
    */

    public boolean changType(int id,int type){
        boolean flag=false;
        try{
            String updateSql="update rms_users set type=? where id=?";
            PreparedStatement ppStatement=mysqlDriver.getPPStatement(updateSql);
            ppStatement.setInt(1,type);
            ppStatement.setInt(2,id);
            if(0==ppStatement.executeUpdate()){
                flag=false;
            }else{
                flag=true;
            }
        }catch(SQLException e){

        }finally{
            mysqlDriver.close();
        }
        return flag;
    }




    /**
    *empty() 清空操作 危险
    *@ return boolean 是否清空成功
    */

    public boolean empty(){

        try{
            PreparedStatement ppStatement=mysqlDriver.getPPStatement("delete from rm_users  ");
            if(0==ppStatement.executeUpdate()){
                return false;
            }else{
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            mysqlDriver.close();
        }
        return true;
    }

    /**
    *changePassword() 修改密码操作
    *@ return boolean 是否修改成功 TODO 返回错误信息需要修改为数组
    *@ param int id ID值
    *@ param String username 账户
    *@ param String oldPassword 久的密码
    *@ param String password 新的密码
    */
    public boolean changePassword(int id,String username,String oldPassword,String password){
        if (id==0) return false;
        try{
            String checkSql="select username from rms_users where password=\""+oldPassword+"\"";
            ResultSet rs=mysqlDriver.query(checkSql);
            if(rs.next()){
                if(rs.getString("username").equals(username)){
                    return false;
                }else{
                    PreparedStatement ppStatement=mysqlDriver.getPPStatement("update rms_users set password=? where id=?");
                    ppStatement.setString(1,password);
                    ppStatement.setInt(2,id);
                    if(0==ppStatement.executeUpdate()){
                        return false;
                    }else{
                        return true;
                    }
                }
            }else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            mysqlDriver.close();
        }
        return true;
    }



}//end class
