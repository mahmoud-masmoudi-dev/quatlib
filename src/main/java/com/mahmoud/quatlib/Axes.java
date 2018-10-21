package com.mahmoud.quatlib;

import android.content.Context;
import android.widget.RelativeLayout;

public class Axes {
    private static final String TAG = Axes.class.getSimpleName();

    // Variables -----------------------------------------------------------------------------------
    private RelativeLayout mParent;
    private Context mContext;
    private double[] mCenterScreen; // Coordinates in the parent layout in screen unit
    private double mXUnitLengthScreen; // Length of one unit on the x-axis in screen unit
    private double mYUnitLengthScreen; // Length of one unit on the y-axis in screen unit
    private double mXZoom;
    private double mYZoom;

    // Functions -----------------------------------------------------------------------------------
    public Axes(Context context, RelativeLayout parent) {
        this.mContext = context;
        this.mParent = parent;

        // Default center : center of the parent
        this.mCenterScreen = new double[]{
                (parent.getLeft() + parent.getRight())/2,
                (parent.getTop() + parent.getBottom())/2
        };

        // Default x-units : 1 in axes unit at the right of the parent
        this.mXUnitLengthScreen = (parent.getRight() - parent.getLeft())/2;
        this.mXZoom = 1;
        // Default y-units : 1 in axes unit at the top of the parent
        this.mYUnitLengthScreen = (parent.getBottom() - parent.getTop())/2;
        this.mYZoom = 1;
    }

    public Axes(Context context, RelativeLayout parent, double[] centerScreen) {
        this.mContext = context;
        this.mParent = parent;
        this.mCenterScreen = centerScreen;

        // Default x-units : 1 in axes unit at the right of the parent
        this.mXUnitLengthScreen = (parent.getRight() - parent.getLeft())/2;
        this.mXZoom = 1;
        // Default y-units : 1 in axes unit at the top of the parent
        this.mYUnitLengthScreen = (parent.getBottom() - parent.getTop())/2;
        this.mYZoom = 1;
    }

    public Axes(Context context, RelativeLayout parent, double xZoom, double yZoom) {
        this.mContext = context;
        this.mParent = parent;

        // Default center : center of the parent
        this.mCenterScreen = new double[]{
                (parent.getLeft() + parent.getRight())/2,
                (parent.getTop() + parent.getBottom())/2
        };

        this.mXZoom = xZoom;
        this.mYZoom = yZoom;

        this.mXUnitLengthScreen = xZoom*(parent.getRight() - parent.getLeft())/2;
        this.mYUnitLengthScreen = yZoom*(parent.getBottom() - parent.getTop())/2;
    }

    public Axes(Context context, RelativeLayout parent, double[] centerScreen, double xZoom, double yZoom) {
        this.mContext = context;
        this.mParent = parent;
        this.mCenterScreen = centerScreen;
        this.mXZoom = xZoom;
        this.mYZoom = yZoom;

        this.mXUnitLengthScreen = xZoom*(parent.getRight() - parent.getLeft())/2;
        this.mYUnitLengthScreen = yZoom*(parent.getBottom() - parent.getTop())/2;
    }

    public double[] convertToScreenCoords(double[] coordsAxes) {
        ErrorChecker.assertCondition(Vect.isValid2D(coordsAxes), "Invalid 2D coordinates");

        double[] coordsScreen = new double[Vect.VECT_2D_ARRAY_SIZE];
        coordsScreen[0] = mCenterScreen[0] + coordsAxes[0]* mXUnitLengthScreen;
        coordsScreen[1] = mCenterScreen[1] - coordsAxes[1]* mYUnitLengthScreen;

        return coordsScreen;
    }

    public void drawAxes() {
        ErrorChecker.assertCondition(mContext != null, "Context is not defined");

        LineView hLine = new LineView(mContext);
        LineView vLine = new LineView(mContext);

        // Horizontal line
        hLine.setX1(mParent.getLeft());
        hLine.setX2(mParent.getRight());
        hLine.setY1(mCenterScreen[1]);
        hLine.setY2(mCenterScreen[1]);

        // Vertical line
        vLine.setX1(mCenterScreen[0]);
        vLine.setX2(mCenterScreen[0]);
        vLine.setY1(mParent.getTop());
        vLine.setY2(mParent.getBottom());

        // Add to layout
        mParent.addView(hLine);
        mParent.addView(vLine);

        drawTicks();
    }

    public void drawTicks() {
        ErrorChecker.assertCondition(mContext != null, "Context is not defined");

        LineView xTickLine = new LineView(mContext);
        LineView yTickLine = new LineView(mContext);

        double[] xUnitPoint1 = convertToScreenCoords(new double[]{1, -0.2});
        double[] xUnitPoint2 = convertToScreenCoords(new double[]{1, +0.2});
        double[] yUnitPoint1 = convertToScreenCoords(new double[]{-0.2, 1});
        double[] yUnitPoint2 = convertToScreenCoords(new double[]{0.2, 1});

        // x-axis tick line
        xTickLine.setX1(xUnitPoint1[0]);
        xTickLine.setX2(xUnitPoint2[0]);
        xTickLine.setY1(xUnitPoint1[1]);
        xTickLine.setY2(xUnitPoint2[1]);

        // y-axis tick line
        yTickLine.setX1(yUnitPoint1[0]);
        yTickLine.setX2(yUnitPoint2[0]);
        yTickLine.setY1(yUnitPoint1[1]);
        yTickLine.setY2(yUnitPoint2[1]);

        // Add to layout
        mParent.addView(xTickLine);
        mParent.addView(yTickLine);
    }

    public void drawPoint(double[] pointAxes) {
        ErrorChecker.assertCondition(mContext != null, "Context is not defined");
        ErrorChecker.assertCondition(Vect.isValid2D(pointAxes), "Invalid 2D point");

        PointView point = new PointView(mContext);
        double[] pointScreen = convertToScreenCoords(pointAxes);

        point.setCx(pointScreen[0]);
        point.setCy(pointScreen[1]);
        point.setR(2);

        mParent.addView(point);
    }

    public void drawPoints(Points2D pointsAxes) {
        ErrorChecker.assertCondition(pointsAxes != null, "Argument cannot be null");

        for(int i = 0; i < pointsAxes.getNbVectors(); i++) {
            drawPoint(pointsAxes.getVectAt(i));
        }
    }

    public void clear() {
        mParent.removeAllViews();
        drawAxes();
    }
}
