package com.simon.androiddispatchtouchevent.simulation;


import java.util.ArrayList;
import java.util.List;

/**
 * 注意： 你的一系列操作 从down move up 这是一个事件序列 ，事件分发的时候，这些都会分发，down up 发生后触发onClick
 * 注意：action_donw的时候会重置部分数据所以disallow_intercept 为false 或者为true 不会影响结果 ，都会被设置为false
 * <p>
 * <p>
 * 情景分析 down+ intercepted=true+
 * 从1.0dispatchTouchEvent 收到DectorView发出的事件，然后判断是否是action-down,这个是所有touch 的开始，当判断是down的时候，会重置部分数据  mFirstTouchTarget = null,disallow_intercept = false
 * 如果是首次触发肯定是acition_down,以及通过刚才的重置的数据看，第一次 disallow_intercept = false 所以会执行onInterceptTouchEvent ，如果
 * <p>
 * 情景区分： 不同的action down move和 up相同 不做额外讲解  +  disallow_intercept: true or false  +  onInterceptTouchEvent :true or false  +子view是否处理 true or false
 * <p>
 * 1)action down _  onInterceptTouchEvent :true_子view是否处理 true
 * <p>
 * 从1.0dispatchTouchEvent 收到DectorView发出的事件，然后判断是2.0 down然后执行3.0, 然后执行onInterceptTouchEvent :true ,此时执行mFirstTouchTarget = null => 交由7.0 super.dispatchTouchEvent(ev)处理
 * <p>
 * 2）action up _ disallow_intercept: true onInterceptTouchEvent :true_子view是否处理 true ,接着上面的 1)
 * 此时mFirstTouchTarget == null 且不是down 直接intercept: true 直接 7.0
 * <p>
 * 3）action up _disallow_intercept: false _  onInterceptTouchEvent :true_子view是否处理 true,接着上面的 1)
 * 此时mFirstTouchTarget == null 且 disallow_intercept: false ，会执行onInterceptTouchEvent => intercepted=true 直接7.0
 *
 * <p>
 * 4)action down _  onInterceptTouchEvent :false _子view是否处理 true
 * 执行方法1.0 然后执行2.0 3.0 然后重置内容，然后执行 onInterceptTouchEvent=> false ->intercepted =>false,然后交给子view 处理
 * 5)action up _ disallow_intercept: true_  onInterceptTouchEvent :false _子view是否处理 true 接上面4）
 * 执行方法1.0 ，mFirstTouchTarget != null=>true ,intercepted = false; 同上子view 处理
 * 5)action up _ disallow_intercept: false  onInterceptTouchEvent :false _子view是否处理 true 接上面4）
 * 执行方法1.0 ，mFirstTouchTarget != null=>true ,然后执行 onInterceptTouchEvent=> false ->intercepted = false; 同上子view 处理
 *
 * 6)action down _  onInterceptTouchEvent :false _子view是否处理 false
 * 执行方法1.0 然后执行方法2.0 然后onInterceptTouchEvent =>false=》intercepted = false 然后 6.0 遍历 mFirstTouchTarget = null => 交由7.0 super.dispatchTouchEvent(ev)处理
 *
 * 7）action up _ disallow_intercept: true_  onInterceptTouchEvent :false _子view是否处理 fasle 接上面6）
 * 执行方法1.0 intercept->true ,后续交由7.0 交由7.0 super.dispatchTouchEvent(ev)处理
 *
 * 8）action up _ disallow_intercept: false_  onInterceptTouchEvent :false _子view是否处理 fasle 接上面6） 同上
 *
 * 9）action up _ disallow_intercept: false_  onInterceptTouchEvent :false/true  _子view是否处理 fasle 接上面6） 同上
 *
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * ViewGroup->View
 */

public class ViewGroup extends View {
    private List<View> views = new ArrayList<>();
    //--用户当前触摸的view对象的相关引用--
    private Object mFirstTouchTarget = null;
    //--是否不允许中断事件的继续传递--  false：允许中断  true：不允许中断 =>指代的是标记位 mGroupFlags的行为
    private boolean disallow_intercept = false;

    private boolean intercepted = false;

    //--模拟添加view到 当前 容器--
    public void addView(View view) {
        views.add(view);
    }

    public List<View> getChildViews() {
        return views;
    }

    //--1.0 Viewgroup的touch事件的开始--
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        //--2.0 当点击的行为是 action_down时，会重置部分数据--
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //--3.0 仅仅是模拟重置，具体方法是--
            //--cancelAndClearTouchTargets(ev);--
            // --resetTouchState();--
            resetTouchState();
        }
        boolean handle = false;

        //--4.0 如果点击事件为 down 或者之前没有相关子view的引用--
        if (ev.getAction() == MotionEvent.ACTION_DOWN
                || mFirstTouchTarget != null) {
            //5.0 如果不允许中断则执行onInterceptTouchEvent，反之 intercepted 为false
            //此处就是子view 使用requestDisallowInterceptTouchEvent 来修改影响的地方
            if (!disallow_intercept) {
                intercepted = onInterceptTouchEvent(ev);
            } else {
                intercepted = false;
            }

        } else {
            //--如果没有mFirstTouchTarget，也就是action_down的时候就被 中断了，所以之后不需要再次判断，直接就是中断的--
            intercepted = true;
        }

        //--6.0 如果不拦截则获取子view并尝试分发--
        if (!intercepted) {
            List<View> childs = getChildViews();
            for (View view : childs) {
                //遍历view 如果当前的没分发成功，则试下一个，此前 源码时会判断是不是当前点击的View此处省略
                if (view.dispatchTouchEvent(ev)) {
                    mFirstTouchTarget = view;//此处只是模拟操作，真正的是 mFirstTouchTarget 会保存对view的引用，还有其他信息，view只是他的成员变量
                    handle = true; //如果有子view 消耗了，此处就是返回true，表示整体事件被消耗
                    break;
                }
            }

        }

        //--7.0 此处如果返回null 说明 子view 并没做处理，交由当前容器的父容器处理--
        if (mFirstTouchTarget == null) {
            //--如果子view没处理则交由Viewgroup的父类处理，也就是吧ViewGroup当view 处理，让其自身 消耗此事件--
            handle = super.dispatchTouchEvent(ev);
        }

        return handle;


    }

    //--view group 的 onInterceptTouchEvent 默认返回false ,当然也有其他情况--
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //省略...
        return false;
    }

    //--用于重置当前的touch 数据--
    private void resetTouchState() {
        mFirstTouchTarget = null;
        disallow_intercept = false;
    }

    //--用于子view 请求父view 不要中断 事件传递--
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        disallow_intercept = disallowIntercept;
    }

}
