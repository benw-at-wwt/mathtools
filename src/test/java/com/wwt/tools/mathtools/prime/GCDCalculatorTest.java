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

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("SpellCheckingInspection")
public class GCDCalculatorTest {

    @Test
    public void getIterativeGCDTest() {
        GreatestCommonDivisorAlgorithm calc = IterativeGCDAlgorithm.getInstance();
        assertEquals(5,calc.getGreatestCommonDivisor(25,5));
        assertEquals(3,calc.getGreatestCommonDivisor(3,39));
        assertEquals(1,calc.getGreatestCommonDivisor(17,4523));
        assertEquals(1,calc.getGreatestCommonDivisor(1,39));
        assertEquals(6,calc.getGreatestCommonDivisor(12,18));
        assertEquals(1,calc.getGreatestCommonDivisor(5456,1));
        assertEquals(4,calc.getGreatestCommonDivisor(12356,1544));
        assertEquals(4,calc.getGreatestCommonDivisor(12356,-1544));
        assertEquals(4,calc.getGreatestCommonDivisor(-12356,-1544));


    }

    @Test
    public void getRecursiveGCDTest() {
        GreatestCommonDivisorAlgorithm calc = RecursiveGCDAlgorithm.getInstance();
        assertEquals(5,calc.getGreatestCommonDivisor(25,5));
        assertEquals(3,calc.getGreatestCommonDivisor(3,39));
        assertEquals(1,calc.getGreatestCommonDivisor(17,4523));
        assertEquals(1,calc.getGreatestCommonDivisor(1,39));
        assertEquals(6,calc.getGreatestCommonDivisor(12,18));
        assertEquals(1,calc.getGreatestCommonDivisor(5456,1));
        assertEquals(4,calc.getGreatestCommonDivisor(12356,1544));
        assertEquals(4,calc.getGreatestCommonDivisor(12356,-1544));
        assertEquals(4,calc.getGreatestCommonDivisor(-12356,-1544));

    }
    @Test
    public void speedTest() {
        GreatestCommonDivisorAlgorithm rcalc = RecursiveGCDAlgorithm.getInstance();
        GreatestCommonDivisorAlgorithm icalc = IterativeGCDAlgorithm.getInstance();
        int iterations = 10;
        long lowerBoundRandom = -10000;
        long upperBoundRandom = 10000;
        int sampleSize = 30;
        long [][] toTest  = new long[sampleSize][2];
        IntStream.range(0, sampleSize-1).forEach(i-> toTest[i][0] = ThreadLocalRandom.current().nextLong(lowerBoundRandom,upperBoundRandom));
        IntStream.range(0, sampleSize-1).forEach(i-> toTest[i][1] = ThreadLocalRandom.current().nextLong(lowerBoundRandom,upperBoundRandom));

        LocalDateTime start = LocalDateTime.now();
        IntStream.range(0, iterations).forEach(i->icalc.getGreatestCommonDivisor(toTest[i%sampleSize][0],toTest[i%sampleSize][1]));
        LocalDateTime end = LocalDateTime.now();
        long ims = start.until(end,ChronoUnit.MILLIS);
        //System.out.println("IterativeGCD (ms):"+ims);

        start = LocalDateTime.now();
        IntStream.range(0, iterations).forEach(i->rcalc.getGreatestCommonDivisor(toTest[i%sampleSize][0],toTest[i%sampleSize][1]));
        end = LocalDateTime.now();
        long rms = start.until(end,ChronoUnit.MILLIS);
        //System.out.println("RecursiveGCD (ms) :"+rms);
        assertTrue(true);
    }
}
