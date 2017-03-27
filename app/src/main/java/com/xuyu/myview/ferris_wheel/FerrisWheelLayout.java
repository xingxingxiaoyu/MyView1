package com.xuyu.myview.ferris_wheel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by: xuyu
 * <p>
 * on: 2017/3/23.
 * <p>
 * 描述：输入类描述
 */
public class FerrisWheelLayout extends ViewGroup
{
    private double angle = 0F;
    private int DEFAULT_SIZE = 200;
    private String TAG = "FerrisWheelLayout";
    private Paint mPaint;

    public FerrisWheelLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public FerrisWheelLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public FerrisWheelLayout(Context context)
    {
        this(context, null);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr)
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measure(widthMeasureSpec);
        int height = measure(widthMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = childAt.getLayoutParams();
            layoutParams.width = Dp2Px(FerrisWheelLayout.this.getContext(), 40);
            layoutParams.height = Dp2Px(FerrisWheelLayout.this.getContext(), 40);
        }
        int length = Math.min(width, height);
        setMeasuredDimension(length, length);
    }

    private int measure(int widthMeasureSpec)
    {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode)
        {
            case MeasureSpec.AT_MOST:
                return Math.min(size, DEFAULT_SIZE);
            case MeasureSpec.EXACTLY:
                return size;
            case MeasureSpec.UNSPECIFIED:
                return DEFAULT_SIZE;
            default:
                return 0;
        }
    }

    private int centerX;
    private int centerY;
    private int radius;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        Log.e(TAG, "l" + l + " t" + t + " r" + r + " b" + b);
        centerX = (r - l) / 2;
        centerY = (b - t) / 2;
        radius = Math.min((r - l) / 2, (b - t) / 2) - 30;
        Log.e(TAG, "centerX:" + centerX + "centerY:" + centerY + "radius:" + radius);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            final int index = i;
            View child = getChildAt(i);
            LayoutParams layoutParams = child.getLayoutParams();
            int childWidth = layoutParams.width;
            int childHeight = layoutParams.height;

            child.layout(centerX + (int) (radius * Math.cos((Math.PI * 2) / childCount * i + angle)) - childWidth / 2,
                    centerY + (int) (radius * Math.sin((Math.PI * 2) / childCount * i + angle)) - childHeight / 2,
                    centerX + (int) (radius * Math.cos((Math.PI * 2) / childCount * i + angle)) + childWidth / 2,
                    centerY + (int) (radius * Math.sin((Math.PI * 2) / childCount * i + angle)) + childWidth / 2);
            child.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(FerrisWheelLayout.this.getContext(), "这是第" + index + "张图片", Toast.LENGTH_SHORT).show();
                }
            });
            Log.e(TAG, i + " left:" + (centerX + (int) (radius * Math.cos((Math.PI * 2) / childCount * i + angle)) - childWidth / 2) +
                    " top:" + (centerY + (int) (radius * Math.sin((Math.PI * 2) / childCount * i + angle)) - childHeight / 2) +
                    " right:" + (centerX + (int) (radius * Math.cos((Math.PI * 2) / childCount * i + angle)) + childWidth / 2) +
                    " bottom:" + (centerY + (int) (radius * Math.sin((Math.PI * 2) / childCount * i + angle)) + childWidth / 2));
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.translate(centerX, centerY);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            canvas.drawLine(0, 0, (int) (radius * Math.cos((Math.PI * 2) / childCount * i + angle))
                    , (int) (radius * Math.sin((Math.PI * 2) / childCount * i + angle)), mPaint);
        }
        canvas.translate(-centerX, -centerY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
//            case MotionEvent
        }
        return super.onTouchEvent(event);
    }

    public int Dp2Px(Context context, float dp)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public int Px2Dp(Context context, float px)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
