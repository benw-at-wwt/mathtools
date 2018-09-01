package com.wwt.tools.mathtools.vector;

import java.util.Arrays;

/**
 * Immutable implementation of Vector as double array
 * @author benw@wwt
 */
public class VectorImplementation implements Vector{

    private final double [] vector;
    private int hashCode;

    /**
     * protected constructor for subclassing, so that the implementation can be extended with new operations
     * For creation use the static factory method.
     * @param vector
     */
    @SuppressWarnings("WeakerAccess")
    protected VectorImplementation(double [] vector) {
        this.vector = vector;
    }

    @Override
    public int getDimension() {
        return vector.length;
    }

    @Override
    public double getValueAt(int index) {
        return vector[index];
    }

    @Override
    public double getScalarProduct(Vector v) {
        if(this.getDimension() != v.getDimension()) throw new IllegalArgumentException("dimensions do not fit");
        double returnValue = 0;
        for(int i=0;i<this.getDimension();i++) {
            returnValue +=this.getValueAt(i)*v.getValueAt(i);
        }
        return returnValue;
    }

    @Override
    public Vector multiply(double scalar) {
        double [] returnValue = new double[this.getDimension()];
        for(int i=0;i<returnValue.length;i++) {
            returnValue[i] = getValueAt(i)*scalar;
        }
        return new VectorImplementation(returnValue);
    }

    @Override
    public Vector add(Vector v) {
        if(this.getDimension()!= v.getDimension()) throw new IllegalArgumentException("vector dimensions are not equal");
        double [] returnValue = new double[this.getDimension()];
        for(int i=0;i<returnValue.length;i++) {
            returnValue[i] = this.getValueAt(i)+v.getValueAt(i);
        }
        return new VectorImplementation(returnValue);
    }

    @Override
    public Vector switchValues(int index1, int index2) {
        if(0 > index1 || index1 > getDimension() -1 || 0 > index2 || index2 > getDimension() ) throw new IllegalArgumentException("index out of range");
        double [] returnValue = Arrays.copyOf(vector,vector.length);
        double tmp = returnValue[index1];
        returnValue[index1] = returnValue[index2];
        returnValue[index2] = tmp;
        return new VectorImplementation(returnValue);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("VectorImplementation{ \n");
        for(int i=0;i<this.getDimension();i++) {
            s.append(vector[i]);
            s.append("\n");
        }
        s.append("}");
        return s.toString();
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Vector))return false;
        Vector v = (Vector) other;
        if (v.getDimension() != this.getDimension()) return false;
        for (int i = 0; i < this.getDimension(); i++) {
            if(this.getValueAt(i) != v.getValueAt(i)) return false;
        }
        return(true);
    }

    @Override
    public int hashCode() {
        int result = hashCode;
        if(hashCode == 0) {
            for (int i = 0; i < this.getDimension(); i++) {
                result = result * 31 + Double.hashCode(this.getValueAt(i));
            }
            hashCode = result;
        }
        return result;
    }

    /**
     * Please note that a copy of the input array is created to guarantee immutability
     *
     * @param vector
     * @return
     */
    public static VectorImplementation from(double [] vector) {
        double [] vectorCopy = Arrays.copyOf(vector,vector.length);
        return new VectorImplementation(vectorCopy);
    }



}