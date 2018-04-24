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
public class PollRunableImpl implements Runnable {
    private long initTime;
    private long delay;

    /**
     * @param initTime,unit:seconds
     * @param delay,unit:seconds
     */
    public PollRunableImpl(long initTime, long delay) {
        this.initTime = initTime;
        this.delay = delay;
    }

    @Override
    public void run() {
        int i=1;
        boolean firstFlag = true;
        while(true){//不断尝试调用任务执行方法，判断时间是否为可触发时间
            if(firstFlag==true){
                try {
                    TimeUnit.SECONDS.sleep(initTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第"+i+"次执行");
                long currentTime = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("任务执行时间："+sdf.format(new Date(currentTime)));
                i++;
                firstFlag = false;
            }else{
                try {
                    TimeUnit.SECONDS.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                    System.out.println("第"+i+"次执行");
                    long currentTime = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    System.out.println("任务执行时间："+sdf.format(new Date(currentTime)));
                    i++;
            }
        }
    }
}
