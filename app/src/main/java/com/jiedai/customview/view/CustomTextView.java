package com.jiedai.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.jiedai.customview.R;
import com.orhanobut.logger.Logger;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Create by yuheng
 * date：2019/4/16
 * description：
 */
public class CustomTextView extends View {
    private Context context = null;
    private Paint paint = null;
    private Rect bound = null;
    private String drawText = "";

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        drawText = typedArray.getString(R.styleable.CustomTextView_customText);
        int textColor = typedArray.getColor(R.styleable.CustomTextView_customTextColor, Color.RED);
        int textSize = typedArray.getDimensionPixelSize(R.styleable.CustomTextView_customTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        typedArray.recycle();

        paint = new Paint();
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        bound = new Rect();
        paint.getTextBounds(drawText, 0, drawText == null ? 0 : drawText.length(), bound);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawText = randomText();
                postInvalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;
        if (widthSpecMode == MeasureSpec.EXACTLY) {
            width = widthSpecSize;
        } else {
            width = getPaddingStart() + getPaddingEnd() + bound.width();
        }

        if (heightSpecMode == MeasureSpec.EXACTLY) {
            height = heightSpecSize;
        } else {
            height = getPaddingTop() + getPaddingBottom() + bound.height();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Logger.d("width: " + getMeasuredWidth() + " height: " + getMeasuredHeight());
        canvas.drawText(drawText, getMeasuredWidth() / 2 - bound.width() / 2, getMeasuredHeight() / 2 + bound.height() / 2, paint);
    }

    private String randomText() {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4) {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set) {
            sb.append("" + i);
        }
        return sb.toString();
    }

}
