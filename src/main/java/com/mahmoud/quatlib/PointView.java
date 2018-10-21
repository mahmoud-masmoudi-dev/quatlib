package com.mahmoud.quatlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class PointView extends View {
    public static final String TAG = PointView.class.getSimpleName();

    // Variables -----------------------------------------------------------------------------------
    double cx = 0, cy = 0, r = 0;

    // Functions -----------------------------------------------------------------------------------
    public PointView(Context context) {
        super(context);
    }

    public PointView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCx(double cx) {
        this.cx = cx;
    }

    public void setCy(double cy) {
        this.cy = cy;
    }

    public void setR(double r) {
        this.r = r;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle((float)cx, (float)cy, (float)r, new Paint());
    }
}
