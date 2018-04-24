package schedule;

/**
 * 循环调用实现定时任务
 *
 * @author xujian
 * @create 2018-04-19 17:11
 **/
public class InvokeSchedule {
    public void fun1(){
        fun2();
    }
    public void fun2(){
        fun1();
    }
}
