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
package com.wwt.tools.mathtools.prime;

import com.wwt.tools.mathtools.MathTool;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementation of Fermat prime test, GCD calculator can be provided
 *
 * @author benw@wwt
 */
public final class FermatPrimeTest implements ProbabilisticPrimeTest {

    private final GreatestCommonDivisorAlgorithm gcdCalculator;

    private FermatPrimeTest(GreatestCommonDivisorAlgorithm gcdCalculator) {
        this.gcdCalculator = gcdCalculator;
    }

    @Override
    public boolean isPrime(long numberToTest, int iterations) {
        if(numberToTest == 2 || numberToTest == 3 || numberToTest == 5) return true;
        if(numberToTest == 0 || numberToTest == 1 || numberToTest % 2 == 0) return false;

        for(int i=0;i<iterations;i++) {
            long a = 2 + (Math.abs(ThreadLocalRandom.current().nextLong()) % (numberToTest-3));
            if(gcdCalculator.getGreatestCommonDivisor(a,numberToTest)!= 1) return false;
            if(MathTool.exponentiationModulo(a,numberToTest-1,numberToTest) != 1) return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "FermatPrimeTest{" +
                "gcdCalculator=" + gcdCalculator +
                '}';
    }

    /**
     * For future caching of objects if needed
     *
     * @param gcdCalculator
     * @return
     */
    public static ProbabilisticPrimeTest getInstance(GreatestCommonDivisorAlgorithm gcdCalculator) {
        return new FermatPrimeTest(gcdCalculator);
    }
}
