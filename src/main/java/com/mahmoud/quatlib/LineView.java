package com.mahmoud.quatlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class LineView extends View {
    private static final String TAG = LineView.class.getSimpleName();

    // Variables -----------------------------------------------------------------------------------
    double x1 = 0, y1 = 0, x2 = 0, y2 = 0;

    // Functions -----------------------------------------------------------------------------------
    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine((float)x1, (float)y1, (float)x2, (float)y2, new Paint());
    }
}
