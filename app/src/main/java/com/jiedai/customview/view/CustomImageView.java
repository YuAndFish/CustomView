package com.jiedai.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.jiedai.customview.R;
import com.orhanobut.logger.Logger;

/**
 * Create by yuheng
 * date：2019/4/16
 * description：
 */
public class CustomImageView extends View {
    private static final int IMAGE_SCALE_FITXY = 0;

    private Paint paint = null;
    private String text = "";
    private int textColor = 0;
    private int textSize = 0;
    private Bitmap imageBitmap = null;
    private int scaleType = 0;

    private Rect rect = null;
    private Rect textBound = null;
    private int width = 0;
    private int height = 0;
    private int imagePadding = 0;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView);
        text = array.getString(R.styleable.CustomImageView_customImageText);
        textColor = array.getColor(R.styleable.CustomImageView_customImageTextColor, Color.RED);
        textSize = array.getDimensionPixelSize(R.styleable.CustomImageView_customImageTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        imagePadding = array.getDimensionPixelSize(R.styleable.CustomImageView_customImagePadding,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
        imageBitmap = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.CustomImageView_customImage, 0));
        scaleType = array.getInt(R.styleable.CustomImageView_imageScaleType, 0);
        array.recycle();

        paint = new Paint();
        paint.setTextSize(textSize);
        textBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBound);
        rect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int desireByImage = getPaddingStart() + getPaddingEnd() + imageBitmap.getWidth();
            int desireByText = getPaddingStart() + getPaddingEnd() + textBound.width();
            if (widthMode == MeasureSpec.AT_MOST) {
                int desire = Math.max(desireByImage, desireByText);
                width = Math.min(widthSize, desire);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            Logger.d("imagePadding: " + imagePadding);
            int desire = getPaddingTop() + getPaddingBottom() + imageBitmap.getHeight() + textBound.height() + imagePadding;
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(heightSize, desire);
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

        rect.left = getPaddingLeft();
        rect.right = width - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = height - getPaddingBottom();
        paint.setColor(textColor);
        paint.setStyle(Paint.Style.FILL);

        //当前设置的宽度小于字体需要的宽度，将字体改为xxx...
        if (textBound.width() > rect.width()) {
            TextPaint textPaint = new TextPaint(paint);
            String msg = TextUtils.ellipsize(text, textPaint, rect.width(), TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingStart(), height - getPaddingBottom(), paint);
        } else {
            canvas.drawText(text, width / 2 - textBound.width() / 2, height - getPaddingBottom(), paint);
        }

        rect.bottom -= textBound.height();
        if (scaleType == IMAGE_SCALE_FITXY) {
            canvas.drawBitmap(imageBitmap, null, rect, paint);
        } else {
            rect.left = width / 2 - imageBitmap.getWidth() / 2;
            rect.right = width / 2 + imageBitmap.getWidth() / 2;
            rect.top = (height - textBound.height()) / 2 - imageBitmap.getHeight() / 2;
            rect.bottom = (height - textBound.height()) / 2 + imageBitmap.getHeight() / 2;
            canvas.drawBitmap(imageBitmap, null, rect, paint);
        }
    }

}
