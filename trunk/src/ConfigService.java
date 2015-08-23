package cn.freeliver.config;
public class ConfigService {
    public ConfigService(){}
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
    public String[] getGlobalConfig(){
        //快捷菜单项目 |||当前登录IP|||客户端浏览器环境|||客户端系统
        return new String[]{"基本设置,高级设置,操作日志,退出","202.117.11.2","IE 8.0","windows 7.0"};
    }
}
