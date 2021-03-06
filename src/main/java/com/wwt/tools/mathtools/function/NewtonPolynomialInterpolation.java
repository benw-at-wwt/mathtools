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

/**
 * Implementation of polynomial interpolation newton algorithm
 *
 * @author benw@wwt
 */
public class NewtonPolynomialInterpolation implements PolynomialInterpolation {

    private static class NewtonPolynomialInterpolationHolder {
        private static final NewtonPolynomialInterpolation INSTANCE = new NewtonPolynomialInterpolation();
    }


    /**
     * not instantiatable -> singleton because object is stateless
     */
    private NewtonPolynomialInterpolation() {}

    @Override
    public Polynomial interpolatePolynomial(double [] x,double [] fx) {
        Polynomial returnValue = from(new double [] {fx[0]});
        int countConsideredPoints = Math.min(x.length,fx.length);
        double [][] coefficients=new double[countConsideredPoints][countConsideredPoints];
        coefficients[0] = fx;
        for(int i=1;i<countConsideredPoints;i++) {
            for(int j=0;j<countConsideredPoints-i;j++) {
                coefficients[i][j] = (coefficients[i - 1][j + 1] - coefficients[i - 1][j]) / (x[j + i] - x[j]);
            }
            Polynomial tmp = from(new double[]{coefficients[i][0]});
            for(int j=0;j<i;j++) {
                tmp  = tmp .multiply(from(new double[]{-x[j],1.}));
            }
            returnValue = returnValue.add(tmp );
        }
        return returnValue;
    }

    @Override
    public String toString() {
        return "NewtonPolynomialInterpolation{}";
    }

    private Polynomial from(double [] arr) {
        return ArrayPolynomial.from(arr);
    }

    /**
     * Singleton instance
     *
     * @return the one and only NewtonPolynomialInterpolation instance
     */
    public static NewtonPolynomialInterpolation getInstance() {
        return NewtonPolynomialInterpolationHolder.INSTANCE;
    }
}
