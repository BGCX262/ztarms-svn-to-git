package cn.freeliver.util;
import java.util.TimeZone;
import java.util.Calendar;
import java.util.Date;

/**
*@author freeliver
*@email freeliver<freeliver204@gmail.com>
*@lastModifiedDate 2010/4/14 9:51
*/

public class U  {
    private static String tz="Asia/Shanghai";
    public static void main(String[] args)
    {
        System.out.println(U.getUTCTime());
    }

    public static long getUTCTime(){
        TimeZone timezone= TimeZone.getTimeZone(U.tz);
        Calendar calendar = Calendar.getInstance(timezone);
        return calendar.getTime().getTime();
    }
    public static String dateFormat(long time){
        TimeZone timezone= TimeZone.getTimeZone(U.tz);
        Calendar calendar=Calendar.getInstance(timezone);
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DATE)+" "+calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND);
    }

    public static String mkPassword(int length){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            sb.append("*");
        }
        return sb.toString();
    }

    public static void print(Object s){
        System.out.println(s);
    }

}
