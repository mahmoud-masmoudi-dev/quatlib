package com.mahmoud.quatlib;

import java.util.ArrayList;
import java.util.List;

public class Points2D {
    public static final String TAG = Points2D.class.getSimpleName();

    // Private functions ---------------------------------------------------------------------------
    List<double[]> mMatrix; // Data matrix

    // Functions -----------------------------------------------------------------------------------
    public Points2D() {
        this.mMatrix = new ArrayList<>();
    }

    public Points2D(int N) {
        this.mMatrix = new ArrayList<>(N);
    }

    public List<double[]> getMatrix() {
        return mMatrix;
    }

    public double[] getVectAt(int index) {
        return mMatrix.get(index);
    }

    public void setVectAt(int i, double[] vect) {
        ErrorChecker.assertCondition(Vect.isValid2D(vect), "Invalid vect");
        ErrorChecker.assertCondition(i < mMatrix.size(), "Invalid index in the inner matrix");

        mMatrix.set(i, vect);
    }

    public int getNbVectors() {
        return mMatrix.size();
    }

    public void addVect(double[] vect) {
        ErrorChecker.assertCondition(Vect.isValid2D(vect), "Invalid vect");

        mMatrix.add(vect);
    }

    public void addVect(double x, double y) {
        double[] vect = new double[]{x, y};
        mMatrix.add(vect);
    }
}
