package com.mahmoud.quatlib;

import java.util.ArrayList;
import java.util.List;

public class Points3D {
    public static final String TAG = Points3D.class.getSimpleName();

    // Variables -----------------------------------------------------------------------------------
    List<double[]> mMatrix; // Data matrix

    // Public functions ----------------------------------------------------------------------------
    public Points3D() {
        this.mMatrix = new ArrayList<>();
    }

    public Points3D(int N) {
        this.mMatrix = new ArrayList<>(N);
    }

    public List<double[]> getMatrix() {
        return mMatrix;
    }

    public double[] getVectAt(int index) {
        return mMatrix.get(index);
    }

    public void setVectAt(int i, double[] vect) {
        ErrorChecker.assertCondition(Vect.isValid(vect), "Invalid vect");
        ErrorChecker.assertCondition(i < mMatrix.size(), "Invalid index in the inner matrix");

        mMatrix.set(i, vect);
    }

    public int getNbVectors() {
        return mMatrix.size();
    }

    public void addVect(double[] vect) {
        ErrorChecker.assertCondition(Vect.isValid(vect), "Invalid vect");

        mMatrix.add(vect);
    }

    public void clear() {
        mMatrix.clear();
    }
}
