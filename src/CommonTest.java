import schedule.TaskExecuteThread;

import java.util.Timer;
import java.util.TreeSet;

/**
 * 通用测试类
 *
 * @author xujian
 * @create 2018-04-23 14:27
 **/
public class CommonTest {
    public static void main(String[] args){
        //创建定时器对象
        Timer t=new Timer();
        //在3秒后执行MyTask类中的run方法,后面每10秒跑一次
        t.schedule(new MyTask2(), 3000,5000);
        t.schedule(new MyTask3(), 5000,10000);
    }
}
