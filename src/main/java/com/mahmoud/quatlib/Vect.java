package com.mahmoud.quatlib;

public class Vect {
    public static final String TAG = Vect.class.getSimpleName();

    // Variables -----------------------------------------------------------------------------------
    public static final int VECT_ARRAY_SIZE = 3;
    public static final int VECT_2D_ARRAY_SIZE = 2;
    public static final double[] NULL = new double[]{0, 0, 0};
    public static final double[] NULL_2D = new double[]{0, 0};
    public static final double[] X = new double[]{1, 0, 0};
    public static final double[] X_INV = new double[]{-1, 0, 0};
    public static final double[] Y = new double[]{0, 1, 0};
    public static final double[] Y_INV = new double[]{0, -1, 0};
    public static final double[] Z = new double[]{0, 0, 1};
    public static final double[] Z_INV = new double[]{0, 0, -1};

    public static final double[] X2D = new double[]{1, 0};
    public static final double[] X2D_INV = new double[]{-1, 0};
    public static final double[] Y2D = new double[]{0, 1};
    public static final double[] Y2D_INV = new double[]{0, -1};

    // Functions -----------------------------------------------------------------------------------
    static boolean isValid(double[] vect) {
        return vect != null && vect.length == VECT_ARRAY_SIZE;
    }

    static boolean isValid2D(double[] vect) {
        return vect != null && vect.length == VECT_2D_ARRAY_SIZE;
    }

    public static double dot(double[] vect1, double[] vect2) {
        ErrorChecker.assertCondition(isValid(vect1) && isValid(vect2), "Invalid vect(s)");

        double sum = 0.0f;
        for(int i = 0; i < VECT_ARRAY_SIZE; i++) {
            sum += vect1[i]*vect2[i];
        }

        return sum;
    }

    public static double[] cross(double[] vect1, double[] vect2) {
        ErrorChecker.assertCondition(isValid(vect1) && isValid(vect2), "Invalid vect(s)");

        double[] outVect = new double[VECT_ARRAY_SIZE];

        outVect[0] = vect1[1]*vect2[2] - vect1[2]*vect2[1];
        outVect[1] = vect2[0]*vect1[2] - vect1[0]*vect2[2];
        outVect[2] = vect1[0]*vect2[1] - vect1[1]*vect2[0];

        return outVect;
    }

    public static double[] add(double[] vect1, double[] vect2) {
        ErrorChecker.assertCondition(isValid(vect1) && isValid(vect2), "Invalid vect(s)");

        double[] vect = new double[VECT_ARRAY_SIZE];

        for(int i = 0; i < VECT_ARRAY_SIZE; i++) {
            vect[i] = vect1[i] + vect2[i];
        }

        return vect;
    }

    public static double[] add2D(double[] vect1, double[] vect2) {
        ErrorChecker.assertCondition(isValid2D(vect1) && isValid2D(vect2), "Invalid vect(s)");

        double[] vect = new double[VECT_2D_ARRAY_SIZE];

        for(int i = 0; i < VECT_2D_ARRAY_SIZE; i++) {
            vect[i] = vect1[i] + vect2[i];
        }

        return vect;
    }

    public static double[] scale(double alpha, double[] inVect) {
        ErrorChecker.assertCondition(isValid(inVect), "Invalid vect");

        double[] outVect = new double[VECT_ARRAY_SIZE];

        for(int i = 0; i < VECT_ARRAY_SIZE; i++) {
            outVect[i] = alpha*inVect[i];
        }

        return outVect;
    }

    public static double[] invert(double[] vect) {
        ErrorChecker.assertCondition(isValid(vect), "Invalid vect");

        return scale(-1, vect);
    }

    public static double norm(double[] vect) {
        double squaredSum = 0.0f;

        for(int i = 0; i < VECT_ARRAY_SIZE; i++) {
            squaredSum += vect[i]*vect[i];
        }

        return (double) Math.sqrt(squaredSum);
    }

    public static double[] normalize(double[] vect) {
        ErrorChecker.assertCondition(isValid(vect), "Invalid vect");

        return scale(1/norm(vect), vect);
    }
}
