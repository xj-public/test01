package schedule;

import java.util.concurrent.TimeUnit;

/**
 * 自定义任务类
 *
 * @author xujian
 * @create 2018-04-23 17:08
 **/
public class MyTask {
    private Runnable runnable;
    private Long nextExecuteTime;
    private Long initTime;
    private Long periodTime;
    private TimeUnit unit;

    public MyTask(Runnable runnable, Long initTime, Long periodTime, TimeUnit unit) {
        this.runnable = runnable;
        this.periodTime = periodTime;
        this.initTime = initTime;
        this.unit = unit;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public Long getNextExecuteTime() {
        return nextExecuteTime;
    }

    public void setNextExecuteTime(Long nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
    }

    public Long getInitTime() {
        return initTime;
    }

    public void setInitTime(Long initTime) {
        this.initTime = initTime;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public Long getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(Long periodTime) {
        this.periodTime = periodTime;
    }
}