package cn.freeliver;
import cn.freeliver.library.MysqlDriver;
import java.sql.*;
import java.util.*;
import static cn.freeliver.util.U.*;

/**
*科研管理信息服务类
*
*@author freeliver
*@email freeliver<freeliver204@gmail.com>
*@version 0.1
*@package cn.freeliver
*@lastModifiedDate 2010-4-20 21:01
*/

  public class ResearchService {

    private MysqlDriver mysqlDriver=null;
    protected String tablePreffix="rms_";
    public ResearchService (){
        mysqlDriver=MysqlDriver.getInstance();
    }

    /**
    *main() 主函数 用于测试
    */

    public static void main(String[] args) {
        ResearchService rs=new ResearchService();
        print(rs.getPageList(1,"declares"));
    }


    /**
    *getPageList() 得到一个分页的数据列表
    *@return ArrayList data 数据列表
    *@param int p 当前页数
    *@param String tableName 操作表
    */

    public ArrayList getPageList(int p,String tableName){
        int page=p<1?1:p;
        //所有记录数
        Double totalCount=0.0;
        //分页每页的记录数
        Double listCount=10.0;
        //总共的页数
        int totalPage=0;
        ArrayList<HashMap>resultSet=new ArrayList<HashMap>();
        try{
            String countSql="select count(id) as totalCount from "+this.tablePreffix+tableName;
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
            String getListSql="select*from "+this.tablePreffix+tableName+" order by cTime Desc limit "+(int)start+","+(int)end;
            rs=mysqlDriver.query(getListSql);
            //放置在前面，在删除的时候将导致效率比较低
            HashMap<String,Integer> meta=new HashMap<String,Integer>();
            meta.put("listCount",listCount.intValue());
            meta.put("totalCount",totalCount.intValue());
            meta.put("totalPage",totalPage);
            resultSet.add(meta);
            while(rs.next()){
                HashMap<String,String> row=new HashMap<String,String>();
                row.put("ID",rs.getString("id"));
                if(tableName.equals("books")){
                    row=this.getBooks(row,rs);
                }else if(tableName.equals("thesis")){
                    row=this.getThesis(row,rs);
               }else if(tableName.equals("apps")){
                   row=this.getApps(row,rs);
               }else if(tableName.equals("results")){
                   row=this.getResults(row,rs);
               }else if(tableName.equals("rewards")){
                   row=this.getRewards(row,rs);
               }else if(tableName.equals("declares")){
                   row=this.getDeclares(row,rs);
               }
                resultSet.add(row);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            mysqlDriver.close();
        }
        return resultSet;
    }

    /**
    *getBooks() 得到论著信息
    *@return HashMap books
    *@param HashMap<String,String>row 行
    *@param ResultSet rs 数据集
    *@access private
    *@throw SQLException
    */

    private HashMap<String,String> getBooks(HashMap<String,String> row,ResultSet rs)throws SQLException{
        try{
            row.put("论著名称",rs.getString("bName"));
            row.put("论著编号",rs.getString("bNumber"));
            row.put("著作类别",rs.getString("bSortname"));
            row.put("出版社",rs.getString("bPress"));
            row.put("学科类别",rs.getString("xSortname"));
            row.put("出版日期",rs.getString("pTime"));
            row.put("版次",rs.getString("pTime"));
            row.put("作者",rs.getString("author"));
            row.put("刊号",rs.getString("pOrder"));
            row.put("备注",rs.getString("remark"));
        }catch(SQLException e){
            throw e;
        }
            return row;
    }

    /**
    *getThesis() 得到论文信息
    *@return HashMap books
    *@param HashMap<String,String>row 行
    *@param ResultSet rs 数据集
    *@access private
    *@throw SQLException
    */

    private HashMap<String,String> getThesis(HashMap<String,String> row,ResultSet rs) throws SQLException{
        try{
            row.put("论文名称",rs.getString("lName"));
            row.put("刊物名称",rs.getString("kName"));
            row.put("刊物类别",rs.getString("kSortname"));
            row.put("刊物级别",rs.getString("kGrade"));
            row.put("主办单位",rs.getString("mPart"));
            row.put("作者",rs.getString("author"));
            row.put("发表时间",rs.getString("pTime"));
            row.put("刊号",rs.getString("kNumber"));
            row.put("备注",rs.getString("remark"));
        }catch(SQLException e){
            throw e;
        }
            return row;
    }

    /**
    *getApps() 得到项目信息
    *@return HashMap books
    *@param HashMap<String,String>row 行
    *@param ResultSet rs 数据集
    *@access private
    *@throw SQLException
    */

    private HashMap<String,String> getApps(HashMap<String,String> row,ResultSet rs) throws SQLException{
        try{
            row.put("项目编号",rs.getString("aNumber"));
            row.put("项目名称",rs.getString("aName"));
            row.put("项目来源",rs.getString("aSource"));
            row.put("项目类别",rs.getString("aSortname"));
            row.put("完成单位",rs.getString("aPart"));
            row.put("负责人",rs.getString("aPrincipal"));
            row.put("课题组成员",rs.getString("aMemebr"));
            row.put("立项时间",rs.getString("bTime"));
            row.put("拟定期限",rs.getString("limitTime"));

            row.put("经费金额",rs.getString("amount"));
            row.put("是否鉴定",rs.getString("isCheck"));
            row.put("鉴定时间",rs.getString("checkTime"));
            row.put("鉴定单位",rs.getString("checkPart"));
            row.put("备注",rs.getString("remark"));
        }catch(SQLException e){
            throw e;
        }
            return row;
    }

    /**
    *getResults() 得到科研结果信息
    *@return HashMap books
    *@param HashMap<String,String>row 行
    *@param ResultSet rs 数据集
    *@access private
    *@throw SQLException
    */

    private HashMap<String,String> getResults(HashMap<String,String> row,ResultSet rs) throws SQLException{
        try{
            row.put("成果名称",rs.getString("rName"));
            row.put("成果类别",rs.getString("rSortname"));
            row.put("完成单位",rs.getString("rPart"));
            row.put("负责人",rs.getString("rPrincipal"));
            row.put("完成时间",rs.getString("rTime"));
            row.put("完成经费金额",rs.getString("amount"));
            row.put("是否投入生产",rs.getString("isProduce"));
            row.put("经济效益",rs.getString("benefits"));
            row.put("备注",rs.getString("remark"));
        }catch(SQLException e){
            throw e;
        }
            return row;
    }

    /**
    *getRewards() 得到科研奖励信息
    *@return HashMap books
    *@param HashMap<String,String>row 行
    *@param ResultSet rs 数据集
    *@access private
    *@throw SQLException
    */

    private HashMap<String,String> getRewards(HashMap<String,String> row,ResultSet rs) throws SQLException{
        try{
            row.put("获奖人员",rs.getString("rPerson"));
            row.put("项目名称",rs.getString("appName"));
            row.put("奖励名称",rs.getString("rName"));
            row.put("授予单位",rs.getString("rPart"));
            row.put("授予时间",rs.getString("rTime"));
            row.put("备注",rs.getString("remark"));
        }catch(SQLException e){
            throw e;
        }
            return row;
    }

    /**
    *getgetDeclares 申请表信息
    *@return HashMap books
    *@param HashMap<String,String>row 行
    *@param ResultSet rs 数据集
    *@access private
    *@throw SQLException
    */

    private HashMap<String,String> getDeclares(HashMap<String,String> row,ResultSet rs) throws SQLException{
        try{
            row.put("项目编号",rs.getString("aNumber"));
            row.put("项目名称",rs.getString("aName"));
            row.put("课题名称",rs.getString("kName"));
            row.put("职称",rs.getString("jobName"));
            row.put("所属部门",rs.getString("sPart"));
            row.put("联系电话",rs.getString("tel"));
            row.put("负责人",rs.getString("aPrincipal"));
            row.put("申报表",rs.getString("dTable"));
            row.put("申报时间",rs.getString("dTime"));
            row.put("申报经费",rs.getString("amount"));

            row.put("是否批准",rs.getString("isAllow"));
            row.put("批准时间",rs.getString("allowTime"));
            row.put("批准经费",rs.getString("allowAmount"));
            row.put("合同时间",rs.getString("agreementTime"));

            row.put("合同内容",rs.getString("agreementContent"));
            row.put("是否结题",rs.getString("isEnd"));
            row.put("结题时间",rs.getString("endTime"));
            row.put("鉴定技术负责人",rs.getString("checkAdmin"));
            row.put("成果效益",rs.getString("resultBenefit"));
            row.put("备注",rs.getString("remark"));
        }catch(SQLException e){
            throw e;
        }
            return row;
    }
    /**
    *addOne() 增加一条记录
    *@return boolean 是否提交成功
    */

    public boolean addOne(HashMap<String,String>data,String tableName){
        if(data==null)return false;
        boolean addOk=false;
        try{
            PreparedStatement ppStatement=mysqlDriver.getPPStatement("insert into "+this.tablePreffix+tableName+" (?)values(?)");
            Set keys=data.keySet();
            Iterator it=keys.iterator();
            String key="";
            String value="";
            StringBuffer fields=new StringBuffer("");
            StringBuffer values=new StringBuffer("");
            while(it.hasNext()){
                key=(String)it.next();
                value=data.get(key);
                fields.append(key);
                values.append("\""+value+"\"");
            }
            String temp1,temp2;
            temp1=fields.toString();
            temp2=values.toString();
            int fieldLen1,fieldLen2;
            fieldLen1=temp1.length();
            fieldLen2=temp2.length();
            String fieldSet=temp1.substring(0,fieldLen1-1);
            String valueSet=temp2.substring(0,fieldLen2-1);
            ppStatement.setString(1,fieldSet);
            ppStatement.setString(2,valueSet);
            int results=ppStatement.executeUpdate();
            if(results==0){
                addOk=false;
            }else{
                addOk=true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            mysqlDriver.close();
        }
        return addOk;
    }


    /**
    *deleteById() 根据ID值删除一条记录
    *@ return boolean 是否删除成功
    *@ param int id ID值
    */

    public boolean deleteById(int id,String tableName){
        if(id==0) return false;
        boolean delOk=false;
        try{
            PreparedStatement pp=mysqlDriver.getPPStatement("delete from "+this.tablePreffix+tableName+" where id=?");
            pp.setInt(1,id);
            int result=pp.executeUpdate();
            if(result==0){
                delOk=false;
            }else{
                delOk=true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            mysqlDriver.close();
        }
        return delOk;
    }

    /**
    *empty() 清空操作 危险
    *@ return boolean 是否清空成功
    */

    public boolean empty(String tableName){
        if(tableName.equals("")||tableName==null) return false;
        boolean emptyOk=false;
        try{
            PreparedStatement pp=mysqlDriver.getPPStatement("delete from "+this.tablePreffix+tableName);
            int result=pp.executeUpdate();
            if(result==0){
                emptyOk=false;
            }else{
                emptyOk=true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            mysqlDriver.close();
        }
        return emptyOk;
     }

    /**
    *edit() 编辑论文信息操作
    *@ return boolean 是否修改成功 TODO 返回错误信息需要修改为数组
    *@ param int id ID值
    */
    public boolean edit(HashMap<String,String>data,int id,String tableName ){
        if(data==null||id==0||tableName==null||tableName.equals(""))return false;
        boolean editOk=false;
        try{
            PreparedStatement ppStatement=mysqlDriver.getPPStatement("update "+this.tablePreffix+tableName+" set ?");
            Set keys=data.keySet();
            Iterator it=keys.iterator();
            String key="";
            String value="";
            StringBuffer k_v=new StringBuffer("");
            while(it.hasNext()){
                key=(String)it.next();
                value=data.get(key);
                k_v.append(key+"=\""+value+"\" ");
            }
            String temp;
            temp=k_v.toString();
            int fieldLen;
            fieldLen=temp.length();
            String k_v_2=temp.substring(0,fieldLen-1);
            ppStatement.setString(1,k_v_2);
            int results=ppStatement.executeUpdate();
            if(results==0){
                editOk=false;
            }else{
                editOk=true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            mysqlDriver.close();
        }
        return editOk;
    }



}//end class
