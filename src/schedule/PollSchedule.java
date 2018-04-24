package schedule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 轮询（不断调用）实现定时执行任务
 *
 * @author xujian
 * @create 2018-04-19 10:42
 **/
public class PollSchedule {
    public static void main(String[] args){
//        PollRunableImpl pollRunable = new PollRunableImpl(5,10);//sleep方式
//        PollRunableImpl2 pollRunable = new PollRunableImpl2(5*1000,10*1000);//wait方式
//        new Thread(pollRunable,"定时任务线程").start();
        TaskExecuteThread taskExecuteThread = new TaskExecuteThread();
        taskExecuteThread.start();
//        try {
//            Thread.sleep(5*1000);
            taskExecuteThread.startShedule(new Runnable() {
                @Override
                public void run() {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long currentTime = System.currentTimeMillis();
                    System.out.println(Thread.currentThread().getName()+"-"+sdf.format(currentTime)+":helloworld");
                }
            },5,10,TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        taskExecuteThread.startShedule(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long currentTime = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName()+"-"+sdf.format(currentTime)+":helloxujian");
            }
        },3,5,TimeUnit.SECONDS);
    }
}
