package com.simon.androiddispatchtouchevent.simulation;


import com.simon.androiddispatchtouchevent.simulation.test.TestActivity;

/**
 * description:  模拟ActivityThread 当应用创建进程的时候会调用此类的main方法，详情可看ActivityThread 源码
 * author: Simon
 * created at 2017/8/30 上午9:05
 * ActivityThread->Activity->onCreate
 */

public class ActivityThread {

    //--模拟main方法--
    public static void main(String[] args) {
        //寻找你的lunch activity
        TestActivity test = new TestActivity();
        test.onCreate();

        //--模拟行为 action_donw--
        test.dispatchTouchEvent(MotionEvent.obtain(MotionEvent.ACTION_DOWN));
        //--模拟行为 action_move--
        test.dispatchTouchEvent(MotionEvent.obtain(MotionEvent.ACTION_MOVE));
        //--模拟行为 action_up--
        test.dispatchTouchEvent(MotionEvent.obtain(MotionEvent.ACTION_UP));
    }
}
