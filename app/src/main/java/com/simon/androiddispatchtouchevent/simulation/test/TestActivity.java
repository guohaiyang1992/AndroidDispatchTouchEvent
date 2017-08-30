package com.simon.androiddispatchtouchevent.simulation.test;


import com.simon.androiddispatchtouchevent.simulation.Activity;

/**
 * description: 用于模拟 你创建的实际的 activity，重写onCreate方法
 * author: Simon
 * created at 2017/8/30 上午9:07
 * onCreate->setContentView()
 */

public class TestActivity extends Activity {
    @Override
    public void onCreate() {
        super.onCreate();
        setContentView(1);
    }
}
