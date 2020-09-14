package com.force.labor.service.test;


import com.force.labor.domain.Grade;
import com.force.labor.domain.Priority;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomStringUtils.random;

public class TestHelper {


    public static String getRandomEightDigits() {
        return random(8, false, true);
    }

    public static String getRandomLetters(int amount) {
        return random(amount, true, false);
    }

    public static Grade getRandomGrade() {
        int pick = new Random().nextInt(Grade.values().length);
        return Grade.values()[pick];
    }

    public static Priority getRandomPriority() {
        int pick = new Random().nextInt(Priority.values().length);
        return Priority.values()[pick];
    }

    public static <T> T getRandomElement(T[] values) {
        return values[getRandomInt(0, values.length - 1)];
    }

    public static <T> T getRandomListElement(List<T> list) {
        return list.get(getRandomInt(0, list.size() - 1));
    }

    public static int getRandomInt() {
        return getRandomInt(0, 100);
    }


    public static int getRandomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("MAX must be bigger than MIN");
        }
        if (min == max) {
            return min;
        }
        int diff = max - min;
        int random = new Random().nextInt(diff + 1);
        return random + min;
    }

    public static long getRandomLong() {
        return new Random().nextLong();
    }

    public static double getRandomDouble() {
        return new Random().nextDouble();
    }


    public static Set<String> getRandomValueSet() {
        Stream<String> infiniteStreamOfRandomUUID = Stream.generate(TestHelper::getRandomEightDigits);
        return infiniteStreamOfRandomUUID.limit(getRandomInt(2, 10)).collect(Collectors.toSet());
    }
}

