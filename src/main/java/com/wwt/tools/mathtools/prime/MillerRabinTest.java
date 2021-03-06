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
 * @author benw@wwt
 */
public final class MillerRabinTest implements ProbabilisticPrimeTest {

    private static class MillerRabinTestHolder {
        private static final MillerRabinTest INSTANCE = new MillerRabinTest();
    }

    /**
     * not instantiatable -> singleton because object is stateless
     */
    private  MillerRabinTest() {}

    @Override
    public boolean isPrime(long numberToTest, int iterations) {
        if (numberToTest == 2 || numberToTest == 3 || numberToTest == 5) {
            return true;
        }
        if (numberToTest == 0 || numberToTest == 1 || numberToTest % 2 == 0) {
            return false;
        }

        long oddRest = numberToTest - 1;
        while (oddRest % 2 == 0) {
            oddRest /= 2;
        }


        for (int i = 0; i < iterations; i++)  {
            long r = Math.abs(ThreadLocalRandom.current().nextLong());
            long a = r % (numberToTest - 1) + 1;
            long temp = oddRest;
            long mod = MathTool.exponentiationModulo(a, temp, numberToTest);
            while (temp != numberToTest - 1 && mod != 1 && mod != numberToTest - 1) {
                mod = MathTool.multiplyModulo(mod, mod, numberToTest);
                temp *= 2;
            }
            if (mod != numberToTest - 1 && temp % 2 == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "MillerRabinTest{}";
    }

    /**
     * Singleton instance
     *
     * @return the one and only MillerRabinTest instance
     */
    public static MillerRabinTest getInstance() { return MillerRabinTestHolder.INSTANCE; }
}