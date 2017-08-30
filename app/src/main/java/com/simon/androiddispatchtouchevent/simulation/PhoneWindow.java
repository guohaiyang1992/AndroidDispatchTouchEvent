package com.simon.androiddispatchtouchevent.simulation;



/**
 * description:过程：首先接收activity通过1.0 superDispatchTouchEvent传递过来的事件
 * 然后此处直接回调2.0 mDecor.superDispatchTouchEvent(ev)，然后回调 3.0的super.dispatchTouchEvent(ev) 也就是转到ViewGroup内
 * author: ghy
 * created at 2017/8/29 下午7:37
 * <p>
 * PhonewWindow->DecorView->ViewGroup
 */

public class PhoneWindow {
    DecorView mDecor;


    //--你是不是好奇DecorView，怎么来的--
    //--没错你调用activity的setContentView--
    //--就会调用PhoneWindow的setContentView--
    // --然后此处会把 你的布局添加到DecorView内--
    public void setContentView(int layoutResID) {
        mDecor = new DecorView();
        View contentView = layoutIdChangeToView(layoutResID);
        mDecor.addView(contentView);
    }

    //--1.0 当 回调此方法时，PhoneWindow会回调DecorView 的对应方法 而DecorView是在setContrentView的时候传入的，也就是你设置的layoutResID 布局的父容器--
    public boolean superDispatchTouchEvent(MotionEvent ev) {
        //-2.0-
        return mDecor.superDispatchTouchEvent(ev);
    }


    //--用于判断是否点击到窗体外部--
    public boolean shouldCloseOnTouch(Activity activity, MotionEvent ev) {
        //判断当前的点击事件是否超出窗体范围达到可关闭的情况，如果是返回true,反之返回false
        return false;
    }


    //--模拟layoutId转view--
    public View layoutIdChangeToView(int layoutResID) {
        //--真正的写法LayoutInflate.from(context).inflate(layoutResID,this,flase)--
        return new View();
    }


    //--decor view 是Phonewindow的内部类--
    class DecorView extends ViewGroup {


        //--3.0 此处也很简单直接回调的是ViewGroup的dispatchTouchEvent--
        public boolean superDispatchTouchEvent(MotionEvent ev) {
            return super.dispatchTouchEvent(ev);
        }
    }

}
