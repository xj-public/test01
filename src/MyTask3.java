import java.text.SimpleDateFormat;
import java.util.TimerTask;

/**
 * a
 *
 * @author xujian
 * @create 2018-04-23 17:47
 **/
class MyTask3 extends TimerTask {
    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getState()+"---"+Thread.currentThread().getName()+"-"+sdf.format(currentTime)+"hello xujian");
    }
}
