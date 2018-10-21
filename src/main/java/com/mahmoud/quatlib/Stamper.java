package com.mahmoud.quatlib;

import android.util.Log;

public class Stamper {
    public static final String TAG = Stamper.class.getSimpleName();

    // Private functions ---------------------------------------------------------------------------
    static double[] cumulativeRotationQuat = Quat.IDENTITY;
    static double[] cumulativeTranslationVect = Vect.NULL;

    // Package-private -----------------------------------------------------------------------------
    static void assertCondition(boolean cond, String errMsg) {
        if(!cond) throw new AssertionError(errMsg);
    }

    // Public functions ----------------------------------------------------------------------------
    public static void resetCumulatives() {
        cumulativeRotationQuat = Quat.IDENTITY;
        cumulativeTranslationVect = Vect.NULL;
    }

    public static double[] stampPointFromSphere(double[] point) {
        assertCondition(Vect.isValid(point), "Invalid point");

        double x = point[0];
        double y = point[1];
        double z = point[2];

        double[] coords2D = new double[Vect.VECT_2D_ARRAY_SIZE];
        if(Vect.dot(point, Vect.Z_INV) == 1) {
            coords2D = Vect.NULL_2D;
        } else if(Vect.dot(point, Vect.Z_INV) == -1) {
            coords2D[0] = (double) Math.PI;
            Log.w(TAG, "Or maybe [0 PI] ??");
        } else {
            double theta, phi;
            theta = (double) (Math.acos(x/Math.sqrt(1 - z*z))*Math.signum(y));
            phi = (double) (Math.PI - Math.acos(z));

            coords2D[0] = (double) (phi*Math.cos(theta));
            coords2D[1] = (double) (phi*Math.sin(theta));
        }

        return coords2D;
    }

    public static double[] stampSphericalDataInitialized(double[] sphericalPoint) {
        ErrorChecker.assertCondition(Vect.isValid(sphericalPoint), "Invalid spherical point");

        double[] originPoint, planePoints;
        originPoint = Quat.rotateVect(sphericalPoint, cumulativeRotationQuat);

        planePoints = stampPointFromSphere(originPoint);
        double[] T = new double[]{
                cumulativeTranslationVect[0],
                cumulativeTranslationVect[1]
        };
        planePoints = Vect.add2D(planePoints, T);

        double theta, phi;
        if(Vect.dot(originPoint, Vect.Z) == 1 || Vect.dot(originPoint, Vect.Z) == -1) {
            theta = 0;
            Log.w(TAG, "Maybe any other theta value ??");
        } else {
            theta = Math.acos(originPoint[0]/Math.sqrt(1 - originPoint[2]*originPoint[2]))*Math.signum(originPoint[1]);
        }
        phi = Math.PI - Math.acos(originPoint[2]);

//        Log.i(TAG, "theta:"+theta+",phi:"+phi);

        // Combine the new rotation with the old cumulated rotations
        cumulativeRotationQuat = Quat.multiply2Quats(Quat.quatFromTwoVectors(originPoint, Vect.Z_INV), cumulativeRotationQuat);
        // Combine translations (to move the sphere)
        double[] translationAmount = new double[Vect.VECT_ARRAY_SIZE];
        translationAmount[0] = phi*Math.cos(theta);
        translationAmount[1] = phi*Math.sin(theta);
        translationAmount[2] = 0;
        cumulativeTranslationVect = Vect.add(cumulativeTranslationVect, translationAmount);

        Log.i(TAG, "cumulativeTranslationVect: "+
                cumulativeTranslationVect[0]+", "+
                cumulativeTranslationVect[1]+", "+
                cumulativeTranslationVect[2]
        );

        return planePoints;
    }
}
