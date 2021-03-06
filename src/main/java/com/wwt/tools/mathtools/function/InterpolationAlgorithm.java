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
 * computes a RealValuedFunction f which satisfies f(x[i]) = fx[i]
 * @author benw@wwt
 */
public interface InterpolationAlgorithm {

    /**
     * returns a function f for which f(x[i])=fx[i] for all i in x.length
     * @param x sequence of x values
     * @param fx sequence of corresponding f(x) values (match by array index)
     * @return function
     */
    RealValuedFunction interpolate(double[] x, double [] fx);
}
