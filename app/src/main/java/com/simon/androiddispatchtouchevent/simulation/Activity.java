package com.simon.androiddispatchtouchevent.simulation;


/**
 * description:  用于模拟的activity 类
 * author: Simon
 * created at 2017/8/29 下午7:04
 * <p>
 * 流程：当系统有了触摸事件将执行 1.0 dispatchTouchEvent，然后此时调用1.1getWindow().superDispatchTouchEvent
 * 然后根据此方法的返回值，具体判断是否执行 2.0 onTouchEvent
 * <p>
 * 额外拓展：当你在Activity的onCreate的内部调用setContentView 的时候，会调用phoneWindow的setContentView方法
 * <p>
 * Activity->PhoneWindow
 */
public class Activity {
    private PhoneWindow window;

    public void onCreate() {
        init();
    }

    //--模拟activity的初始化过程--
    public void init() {
        window = new PhoneWindow();
    }

    //--1.0事件分发的起始位置，此处的事件来至用户触发后系统进行的回调--
    public boolean dispatchTouchEvent(MotionEvent ev) {

        //--1.1此处去分发事件给window，此处的就是phone window--
        //如果window 消耗了事件则此处返回true，事件传递完毕
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        //--2.0 如果上面的window 没有处理则交由activity 的onTouchEvent处理--
        return onTouchEvent(ev);
    }


    //--3.0 activity自身的onTouchEvent事件--
    private boolean onTouchEvent(MotionEvent ev) {
        //获取当前的window，判断是否需要关闭，如果是则执行finish方法，返回true，反之不做操作
        if (getWindow().shouldCloseOnTouch(this, ev)) {
            finish();
            return true;
        }

        return false;
    }

    //--此处模拟的获取window的过程，真实代码中也是获取phonewindow--
    private PhoneWindow getWindow() {
        return window;
    }

    //--此处模拟activity的finish关闭自身--
    public void finish() {

    }

    //--这个就是你使用的setContentView--
    public void setContentView(int layoutResID) {
        window.setContentView(layoutResID);
    }


}
