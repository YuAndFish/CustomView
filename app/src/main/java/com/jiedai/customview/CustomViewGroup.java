package com.jiedai.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Create by yuheng
 * date：2019/4/10
 * description：
 */
public class CustomViewGroup extends ViewGroup {

    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

}
