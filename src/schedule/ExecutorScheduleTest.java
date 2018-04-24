package schedule;

import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 基于线程池的定时任务实现
 *
 * @author xujian
 * @create 2018-04-20 14:57
 **/
public class ExecutorScheduleTest {
    public static void main(String[] args){
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(8);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println(sdf.format(currentTime)+":"+"helloworld");
            }
        },3,5, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println(sdf.format(currentTime)+":"+"嘿嘿嘿");
            }
        },5,10, TimeUnit.SECONDS);
    }
}
