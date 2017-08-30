package com.simon.androiddispatchtouchevent.simulation;


/**
 * description:模拟View类的操作，只保留事件传递代码去除其他无用代码
 * author: Simon
 * created at 2017/8/30 下午3:23
 * // * requestDisallowInterceptTouchEvent 怎么个应用场景，分析的究竟对不对，后面看下别人写的，对比下就知道了
 */

public class View {

    private OnTouchListener mOnTouchListener;


    boolean enable = true;
    private boolean clickAble = true;
    private boolean longClickAble = true;

    public boolean dispatchTouchEvent(MotionEvent ev) {
        //如果lisner 不为null,且enable 则传递到touch lisner 回调，如果返回false 则触发onTouchEvent,反之不触发
        if (mOnTouchListener != null && enable &&
                mOnTouchListener.onTouch(this, ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public interface OnTouchListener {
        /**
         * Called when a touch event is dispatched to a view. This allows listeners to
         * get a chance to respond before the target view.
         *
         * @param v     The view the touch event has been dispatched to.
         * @param event The MotionEvent object containing full information about
         *              the event.
         * @return True if the listener has consumed the event, false otherwise.
         */
        boolean onTouch(View v, MotionEvent event);
    }

    //--设置的touch lisener--
    public void setOnTouchListener(OnTouchListener mOnTouchListener) {
        this.mOnTouchListener = mOnTouchListener;
    }

    //此处自己处理
    public boolean onTouchEvent(MotionEvent ev) {
        //即使此处不是enable的也可返回消耗事件
        if (!enable) {
            return clickAble || longClickAble;
        }

        //如果可以点击 则回调点击事件，返回true 反之返回false
        if (clickAble || longClickAble) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_UP:
                    performClick();
                    break;
            }
            return true;
        } else {
            return false;
        }


    }

    //模拟点击事件回调
    public void performClick() {
    }

}
