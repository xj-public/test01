package schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Runable实现
 *
 * @author xujian
 * @create 2018-04-19 11:40
 **/
public class PollRunableImpl2 implements Runnable {
    final Object lock = new Object();//对象锁
    private long initTime;
    private long delay;

    /**
     * @param initTime,unit:millseconds
     * @param delay,unit:millseconds
     */
    public PollRunableImpl2(long initTime, long delay) {
        this.initTime = initTime;
        this.delay = delay;
    }

    @Override
    public void run() {
        int i=1;
        boolean firstFlag = true;
        long executeTime=0,currentTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentTime = System.currentTimeMillis();
        System.out.println("等待第一次任务执行...当前时间："+sdf.format(new Date(currentTime)));
        synchronized (lock){
            try {
                while(true){//不断尝试调用任务执行方法，判断时间是否为可触发时间
                    currentTime = System.currentTimeMillis();
                    if(firstFlag==true){
                        if(executeTime==0)
                            executeTime = currentTime+initTime;
                        if(currentTime<executeTime){
                            lock.wait(executeTime-currentTime);
                        }
                        System.out.println("第"+i+"次执行");

                        System.out.println("任务执行时间："+sdf.format(new Date(executeTime)));
                        i++;
                        firstFlag = false;
                    }else{
                        executeTime = executeTime+delay;
                        if(currentTime<executeTime){
                            lock.wait(executeTime-currentTime);
                        }
                        System.out.println("第"+i+"次执行");
                        System.out.println("任务执行时间："+sdf.format(new Date(executeTime)));
                        i++;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
