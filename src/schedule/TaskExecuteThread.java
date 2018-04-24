package schedule;

import javax.swing.plaf.SliderUI;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 执行任务的线程
 *
 * @author xujian
 * @create 2018-04-20 15:45
 **/
public class TaskExecuteThread extends Thread {
    private TaskQueue taskQueue = new TaskQueue();
    public void startShedule(Runnable task, long initTime, long periodTime, TimeUnit unit){//仅仅开始执行计划
        MyTask myTask = new MyTask(task,initTime,periodTime,unit);
        long firstExeTiem = System.currentTimeMillis()+unit.toMillis(initTime);
        myTask.setNextExecuteTime(firstExeTiem);
        synchronized (taskQueue){
            taskQueue.add(myTask);//添加到任务集合
            if (taskQueue.getMin() == myTask)
                taskQueue.notify();
        }
    }
    @Override
    public void run(){//真正执行任务
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long thistTime = System.currentTimeMillis();
        System.out.println("当前时间:"+sdf.format(thistTime));
        while (true){
            //线程开启，则开始从任务集合里面拿任务执行
            try{
                synchronized (taskQueue){
                    while (taskQueue.isEmpty())
                        taskQueue.wait();
                    if(taskQueue.isEmpty())
                        break;
                    MyTask task = taskQueue.getMin();//获取堆顶的任务（最先要执行的任务）
                    long currentTime = System.currentTimeMillis();
                    long executeTime = task.getNextExecuteTime();
                    if (executeTime-currentTime<=0){//可以执行任务了
                        taskQueue.rescheduleMin(executeTime+task.getUnit().toMillis(task.getPeriodTime()));
                        task.getRunnable().run();//直接调用run()方法，意味着在当前线程中调用runnable中的普通方法run();如果将runnable放到新的线程执行，则调用start()
                    }else{
                        taskQueue.wait(executeTime-currentTime);
//                        System.out.println(task.getExecuteThread().getState());
//                        task.getExecuteThread().start();
//                        taskQueue.rescheduleMin(executeTime+task.getUnit().toMillis(task.getPeriodTime()));
                    }
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    class TaskQueue {
        /**
         * Priority queue represented as a balanced binary heap: the two children
         * of queue[n] are queue[2*n] and queue[2*n+1].  The priority queue is
         * ordered on the nextExecutionTime field: The TimerTask with the lowest
         * nextExecutionTime is in queue[1] (assuming the queue is nonempty).  For
         * each node n in the heap, and each descendant of n, d,
         * n.nextExecutionTime <= d.nextExecutionTime.
         */
        private MyTask[] queue = new MyTask[128];

        /**
         * The number of tasks in the priority queue.  (The tasks are stored in
         * queue[1] up to queue[size]).
         */
        private int size = 0;

        /**
         * Returns the number of tasks currently on the queue.
         */
        int size() {
            return size;
        }

        /**
         * Adds a new task to the priority queue.
         */
        void add(MyTask task) {
            // Grow backing store if necessary
            if (size + 1 == queue.length)
                queue = Arrays.copyOf(queue, 2*queue.length);

            queue[++size] = task;
            fixUp(size);
        }

        /**
         * Return the "head task" of the priority queue.  (The head task is an
         * task with the lowest nextExecutionTime.)
         */
        MyTask getMin() {
            return queue[1];
        }

        /**
         * Return the ith task in the priority queue, where i ranges from 1 (the
         * head task, which is returned by getMin) to the number of tasks on the
         * queue, inclusive.
         */
        MyTask get(int i) {
            return queue[i];
        }

        /**
         * Remove the head task from the priority queue.
         */
        void removeMin() {
            queue[1] = queue[size];
            queue[size--] = null;  // Drop extra reference to prevent memory leak
            fixDown(1);
        }

        /**
         * Removes the ith element from queue without regard for maintaining
         * the heap invariant.  Recall that queue is one-based, so
         * 1 <= i <= size.
         */
        void quickRemove(int i) {
            assert i <= size;

            queue[i] = queue[size];
            queue[size--] = null;  // Drop extra ref to prevent memory leak
        }

        /**
         * Sets the nextExecutionTime associated with the head task to the
         * specified value, and adjusts priority queue accordingly.
         */
        void rescheduleMin(long newTime) {
            queue[1].setNextExecuteTime(newTime);
            fixDown(1);
        }

        /**
         * Returns true if the priority queue contains no elements.
         */
        boolean isEmpty() {
            return size==0;
        }

        /**
         * Removes all elements from the priority queue.
         */
        void clear() {
            // Null out task references to prevent memory leak
            for (int i=1; i<=size; i++)
                queue[i] = null;

            size = 0;
        }

        /**
         * Establishes the heap invariant (described above) assuming the heap
         * satisfies the invariant except possibly for the leaf-node indexed by k
         * (which may have a nextExecutionTime less than its parent's).
         *
         * This method functions by "promoting" queue[k] up the hierarchy
         * (by swapping it with its parent) repeatedly until queue[k]'s
         * nextExecutionTime is greater than or equal to that of its parent.
         */
        private void fixUp(int k) {
            while (k > 1) {
                int j = k >> 1;
                if (queue[j].getNextExecuteTime() <= queue[k].getNextExecuteTime())
                    break;
                MyTask tmp = queue[j];  queue[j] = queue[k]; queue[k] = tmp;
                k = j;
            }
        }

        /**
         * Establishes the heap invariant (described above) in the subtree
         * rooted at k, which is assumed to satisfy the heap invariant except
         * possibly for node k itself (which may have a nextExecutionTime greater
         * than its children's).
         *
         * This method functions by "demoting" queue[k] down the hierarchy
         * (by swapping it with its smaller child) repeatedly until queue[k]'s
         * nextExecutionTime is less than or equal to those of its children.
         */
        private void fixDown(int k) {
            int j;
            while ((j = k << 1) <= size && j > 0) {
                if (j < size &&
                        queue[j].getNextExecuteTime() > queue[j+1].getNextExecuteTime())
                    j++; // j indexes smallest kid
                if (queue[k].getNextExecuteTime() <= queue[j].getNextExecuteTime())
                    break;
                MyTask tmp = queue[j];  queue[j] = queue[k]; queue[k] = tmp;
                k = j;
            }
        }

        /**
         * Establishes the heap invariant (described above) in the entire tree,
         * assuming nothing about the order of the elements prior to the call.
         */
        void heapify() {
            for (int i = size/2; i >= 1; i--)
                fixDown(i);
        }
    }

}
