package com.simon.androiddispatchtouchevent.simulation;

/**
 * description:  模拟真正的 点击事件
 * author: Simon
 * created at 2017/8/30 上午9:18
 */

public class MotionEvent {
    private int action;
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_UP = 1;
    public static final int ACTION_MOVE = 2;

    private MotionEvent(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public static MotionEvent obtain(int action) {
        return new MotionEvent(action);
    }
}
