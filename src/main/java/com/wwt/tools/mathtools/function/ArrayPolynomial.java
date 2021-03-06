/* Copyright 2018-2019 Wehe Web Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wwt.tools.mathtools.function;

import java.util.Arrays;

/**
 * Immutable implementation of Polynomial as double array of coefficients
 * @author benw@wwt
 */
public class ArrayPolynomial implements Polynomial {


    private final double [] coefficients;
    private int hashCode;

    /**
     * protected constructor for subclassing, so that the implementation can be extended with new operations
     * For creation use the static factory method.
     * @param coefficients
     */
    @SuppressWarnings("WeakerAccess")
    protected ArrayPolynomial(double [] coefficients) {
        this.coefficients = coefficients;
    }

    /**
     * Implementation with Horner Schema
     * @param x
     * @return
     */
    @Override
    public double f(double x) {
        double value=coefficients[coefficients.length-1];
        for(int i=coefficients.length-2;i>=0;i--) {
            value = value  * x + coefficients[i] ;
        }
        return value;
    }

    @Override
    public int getDegree() {
        for(int i = coefficients.length-1;i>= 0; i--) {
            if(coefficients[i] != 0) {
                return i;
            }
        }
        return 0;
    }
    @Override
    public double getCoefficient(int degree){
        if(degree > coefficients.length-1 || degree < 0) {
            return 0;
        }
        return coefficients[degree];
    }

    @Override
    public Polynomial normalize() {
        ArrayPolynomial returnValue = this.constructReturnValue();
        double tmp = returnValue.coefficients[returnValue.coefficients.length-1];

        for(int i=0;i<returnValue.coefficients.length-1;i++) {
            if(returnValue.coefficients[i] != 0) {
                returnValue.coefficients[i] /= tmp;
            }
        }
        returnValue.coefficients[returnValue.coefficients.length-1]=1;
        return returnValue;

    }

    @Override
    public Polynomial multiplyScalar(double scalar) {
        ArrayPolynomial returnValue = this.constructReturnValue();
        for(int i=0;i<returnValue.coefficients.length;i++) {
            returnValue.coefficients[i] *= scalar;
        }
        return returnValue;
    }

    @Override
    public Polynomial differentiate() {
        double [] derivative =new double[coefficients.length-1];
        for(int i=0;i<derivative.length;i++){
            derivative[i] = coefficients[i+1]*(double)(i+1);
        }
        return ArrayPolynomial.from(derivative);
    }


    @Override
    public Polynomial add(Polynomial p2) {
        double [] addedCoefficients = new double[Math.max(this.getDegree(),p2.getDegree())+1];
        for(int i = 0;i<addedCoefficients.length;i++) {
            addedCoefficients[i] = this.getCoefficient(i) + p2.getCoefficient(i);
        }
        return ArrayPolynomial.from(addedCoefficients);

    }

    @Override
    public Polynomial subtract(Polynomial p2) {
        double [] addedCoefficients = new double[Math.max(this.getDegree(),p2.getDegree())+1];
        for(int i = 0;i<addedCoefficients.length;i++) {
            addedCoefficients[i] = this.getCoefficient(i) - p2.getCoefficient(i);
        }
        return ArrayPolynomial.from(addedCoefficients);
    }

    @Override
    public Polynomial multiply(Polynomial p2) {
        double [] multipliedCoefficients=new double[this.getDegree()+p2.getDegree()+1];
        for(int i=0;i<=this.getDegree();i++) {
            for(int j=0;j<=p2.getDegree();j++) {
                multipliedCoefficients[i+j] += this.getCoefficient(i)*p2.getCoefficient(j);
            }
        }
        return ArrayPolynomial.from(multipliedCoefficients);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for(int i = coefficients.length-1;i>= 2; i--) {
            if(coefficients[i] != 0) {
                if(s.length()>1) s.append("+");
                s.append(coefficients[i]).append("*x^").append(i);
            }
        }
        if(coefficients.length> 1 && coefficients[1] != 0 ) {
            s.append("+").append(coefficients[1]).append("*x");
        }
        if(coefficients[0] != 0 ) s.append("+").append(coefficients[0]);
        s.append("]");
        return s.toString();
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Polynomial))return false;
        Polynomial v = (Polynomial) other;
        if (v.getDegree() != this.getDegree()) return false;
        for (int i = 0; i <= this.getDegree(); i++) {
            if(this.getCoefficient(i) != v.getCoefficient(i)) return false;
        }
        return(true);
    }

    @Override
    public int hashCode() {
        int result = hashCode;
        if(hashCode == 0) {
            for (int i = 0; i <= this.getDegree(); i++) {
                result = result * 31 + Double.hashCode(this.getCoefficient(i));
            }
            hashCode = result;
        }
        return result;
    }


    /**
     * Exposed public for storage optimization
     *
     * @return
     */
    public final ArrayPolynomial shrinkArray() {
        if(coefficients[coefficients.length-1] != 0) {
            return this;
        }
        else return (ArrayPolynomial) ArrayPolynomial.from(coefficients);
    }


    private ArrayPolynomial constructReturnValue() {
        if (coefficients[coefficients.length-1] == 0) {
            return this.shrinkArray();
        }
        else {
            double [] coefficientsCopy = Arrays.copyOf(coefficients,coefficients.length);
            return new ArrayPolynomial(coefficientsCopy);
        }
    }

    /**
     * Please note that a copy of the input array is created to guarantee immutability
     *
     * @param coefficients
     * @return
     */
    public static Polynomial from(double [] coefficients) {
        int newSize = 1;
        for(int i = coefficients.length-1;i>= 0; i--) {
            if(coefficients[i] != 0) {
                newSize = i+1;
                break;
            }
        }
        double [] coefficientsCopy = Arrays.copyOf(coefficients,newSize);
        return new ArrayPolynomial(coefficientsCopy);
    }


}
