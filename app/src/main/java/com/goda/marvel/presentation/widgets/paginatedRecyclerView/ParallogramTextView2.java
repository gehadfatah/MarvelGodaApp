package com.goda.marvel.presentation.widgets.paginatedRecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class ParallogramTextView2 extends AppCompatTextView {


    Paint mInnerPaint;

    public ParallogramTextView2(Context context) {
        super(context);
        init();
    }

    public ParallogramTextView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ParallogramTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(Color.parseColor("#FFFFFF"));
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setStrokeJoin(Paint.Join.ROUND);
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Path path = new Path();
        path.moveTo(getWidth(),0);
        path.lineTo(getWidth()/2, 0);
        path.lineTo(0, getHeight());
        path.lineTo(getWidth()/2,getHeight());
        path.lineTo(getWidth(), 0);
        canvas.drawPath(path, mInnerPaint);
    }

}