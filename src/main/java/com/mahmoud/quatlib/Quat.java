package com.mahmoud.quatlib;

public class Quat {
    public static final String TAG = Quat.class.getSimpleName();

    // Variables -----------------------------------------------------------------------------------
    public static final int QUAT_ARRAY_SIZE = 4;
    public static final double[] NULL = new double[]{0, 0, 0, 0};
    public static final double[] IDENTITY = new double[]{1, 0, 0, 0};

    // Private functions ---------------------------------------------------------------------------
    private static boolean isValid(double[] quat) {
        return quat != null && quat.length == QUAT_ARRAY_SIZE;
    }

    // Public functions ----------------------------------------------------------------------------
    public static double[] getVectPart(double[] quat) {
        ErrorChecker.assertCondition(isValid(quat), "Invalid quat");

        double[] vect = new double[Vect.VECT_ARRAY_SIZE];
        for(int i = 1; i < QUAT_ARRAY_SIZE; i++) {
            vect[i-1] = quat[i];
        }

        return vect;
    }

    public static double[] add(double[] quat1, double[] quat2) {
        ErrorChecker.assertCondition(isValid(quat1) && isValid(quat2), "Invalid quat(s)");

        double[] quat = new double[QUAT_ARRAY_SIZE];

        for(int i = 0; i < QUAT_ARRAY_SIZE; i++) {
            quat[i] = quat1[i] + quat2[i];
        }

        return quat;
    }

    public static double[] scale(double alpha, double[] inQuat) {
        ErrorChecker.assertCondition(isValid(inQuat), "Invalid quat");

        double[] outQuat = new double[QUAT_ARRAY_SIZE];

        for(int i = 0; i < QUAT_ARRAY_SIZE; i++) {
            outQuat[i] = alpha*inQuat[i];
        }

        return outQuat;
    }

    public static double[] invert(double[] inQuat) {
        ErrorChecker.assertCondition(isValid(inQuat), "Invalid quat");

        return scale(-1, inQuat);
    }

    public static double norm(double[] quat) {
        double squaredSum = 0.0f;

        for(int i = 0; i < QUAT_ARRAY_SIZE; i++) {
            squaredSum += quat[i]*quat[i];
        }

        return (double) Math.sqrt(squaredSum);
    }

    public static double[] normalize(double[] quat) {
        ErrorChecker.assertCondition(isValid(quat), "Invalid quat");

        return scale(1/norm(quat), quat);
    }

    public static double[] conjQuat(double[] inQuat) {
        ErrorChecker.assertCondition(isValid(inQuat), "Invalid quat");

        double[] outQuat = scale(-1.0f, inQuat);

        outQuat[0] *= -1;

        return outQuat;
    }

    public static double[] multiply2Quats(double[] quat1, double[] quat2) {
        ErrorChecker.assertCondition(isValid(quat1) && isValid(quat2), "Invalid quat(s)");

        double[] quat = new double[QUAT_ARRAY_SIZE];

        double r1 = quat1[0], r2 = quat2[0];
        double[] v1 = getVectPart(quat1), v2 = getVectPart(quat2);

        double[] vectPart = Vect.scale(r1, v2);
        vectPart = Vect.add(vectPart, Vect.scale(r2, v1));
        vectPart = Vect.add(vectPart, Vect.cross(v1, v2));

        quat[0] = r1*r2 - Vect.dot(v1, v2);
        quat[1] = vectPart[0];
        quat[2] = vectPart[1];
        quat[3] = vectPart[2];

        return quat;
    }

    /**
     * Compute a quaternion given an angle of rotation alpha around an axis defined by the vector u
     * @param alpha The amount of rotation in radians
     * @param vect The director vector of the rotation axis
     * @return Quat that makes a rotation about vect's axis with alpha radians
     */
    public static double[] quatFromAngleAndAxis(double alpha, double[] vect) {
        ErrorChecker.assertCondition(Vect.isValid(vect), "Invalid vect");

        double[] quat = new double[QUAT_ARRAY_SIZE];
        double[] vectNormalized = Vect.scale(1/Vect.norm(vect), vect);

        double sinHalfAlpha = (double) Math.sin(alpha/2);

        quat[0] = (double) Math.cos(alpha/2);
        quat[1] = sinHalfAlpha*vectNormalized[0];
        quat[2] = sinHalfAlpha*vectNormalized[1];
        quat[3] = sinHalfAlpha*vectNormalized[2];

        return quat;
    }

    public static double[] quatFromTwoVectors(double[] startVect, double[] endVect) {
        ErrorChecker.assertCondition(Vect.isValid(startVect), "Invalid start vector");
        ErrorChecker.assertCondition(Vect.isValid(endVect), "Invalid end vector");

        double normStartVect = Vect.norm(startVect);
        double normEndVect = Vect.norm(endVect);

        double cosAngle = Vect.dot(startVect, endVect)/(normStartVect*normEndVect);
        double sinAngle = Vect.norm(Vect.cross(startVect, endVect))/(normStartVect*normEndVect);

        double angle;
        if(cosAngle > 1) {
            angle = 0.0f;
        } else if(cosAngle < -1) {
            angle = (double) Math.PI;
        } else if(sinAngle >= 0) {
            angle = (double) Math.acos(cosAngle);
        } else {
            angle = (double) -Math.acos(cosAngle);
        }

        double[] quat = new double[QUAT_ARRAY_SIZE];
        if(angle == 0) {
            quat = IDENTITY;
        } else {
            quat = quatFromAngleAndAxis(angle, Vect.cross(startVect, endVect));
        }

        return quat;
    }

    public static double[] rotateVect(double[] vect, double[] quat) {
        ErrorChecker.assertCondition(Vect.isValid(vect), "Invalid vect");
        ErrorChecker.assertCondition(isValid(quat), "Invalid quat");
//        ErrorChecker.assertCondition(norm(quat) == 1, "Quat is not unit quaternion");

        double[] vectQuat = new double[QUAT_ARRAY_SIZE];
        System.arraycopy(vect, 0, vectQuat, 1, Vect.VECT_ARRAY_SIZE);
        vectQuat[0] = 0;

        double[] resultQuat = multiply2Quats(quat, vectQuat);
        resultQuat = multiply2Quats(resultQuat, conjQuat(quat));

        return getVectPart(resultQuat);
    }
}
