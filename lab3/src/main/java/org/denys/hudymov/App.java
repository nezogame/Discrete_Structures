package org.denys.hudymov;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.util.stream.Collectors.toMap;

public class App {

    public static void explore(Map<String, Long> timestamps, SkipList<Integer> list) {
        StopWatch stopWatch = new StopWatch();
        var firstTest = 8_000_000;
        var secondTest = 10_000_000;
        var thirdTest = 12_000_000;
        var fourthTest = 14_000_000;
        var fifthTest = 16_000_000;
        list.setP(1/2.0f);
        for (int i = 0; i < 5; i++) {
            stopWatch.startMeasurement();
            for (int j = 0; j < firstTest; j++) {
                list.add(j);
            }
            timestamps.put(firstTest + " number: " + i, stopWatch.endMeasurement().toMillis());

            stopWatch.startMeasurement();
            for (int j = 0; j < secondTest; j++) {
                list.add(j);
            }
            timestamps.put(secondTest + " number: " + i, stopWatch.endMeasurement().toMillis());

            list.clean();
            stopWatch.startMeasurement();
            for (int j = 0; j < thirdTest; j++) {
                list.add(j);
            }
            timestamps.put(thirdTest + " number: " + i, stopWatch.endMeasurement().toMillis());

            list.clean();
            stopWatch.startMeasurement();
            for (int j = 0; j < fourthTest; j++) {
                list.add(j);
            }
            timestamps.put(fourthTest + " number: " + i, stopWatch.endMeasurement().toMillis());

            list.clean();
            stopWatch.startMeasurement();
            for (int j = 0; j < fifthTest; j++) {
                list.add(j);
            }
            timestamps.put(fifthTest + " number: " + i, stopWatch.endMeasurement().toMillis());
        }
    }

    public static void exploreMethodFp(Map<String, Long> timestamps, SkipList<Integer> list) {
        StopWatch stopWatch = new StopWatch();
        var firstTest = 10_000;
        var secondTest = 100_000;
        var thirdTest = 1_000_000;
        for (int i = 0; i < 5; i++) {
            stopWatch.startMeasurement();
            for (int j = 0; j < firstTest; j++) {
                list.add(j);
            }
            timestamps.put(firstTest + " number: " + i, stopWatch.endMeasurement().toMillis());

            list.clean();
            stopWatch.startMeasurement();
            for (int j = 0; j < secondTest; j++) {
                list.add(j);
            }
            timestamps.put(secondTest + " number: " + i, stopWatch.endMeasurement().toMillis());

            list.clean();
            stopWatch.startMeasurement();
            for (int j = 0; j < thirdTest; j++) {
                list.add(j);
            }
            timestamps.put(thirdTest + " number: " + i, stopWatch.endMeasurement().toMillis());
        }
    }

    public static void exploreConfidenceInterval(Map<String, Long> timestamps, SkipList<Integer> list) {
        StopWatch stopWatch = new StopWatch();
        var secondTest = 100_000;
        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < secondTest; j++) {
                stopWatch.startMeasurement();
                list.add(j);
                timestamps.put("Number: " + j, stopWatch.endMeasurement().toNanos());
            }
        }
    }

    public static void measureFirst(Map<String, Long> timestamps, SkipList<Integer> list, List<Double> stds) {
        explore(timestamps, list);

        var stats1 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("8000000"))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();
        var stats2 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("10000000"))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();
        var stats3 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("12000000"))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();
        var stats4 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("14000000"))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();
        var stats5 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("16000000"))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();

        var firstArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("8000000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();
        var secondArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("10000000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();
        var thirdArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("12000000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();
        var fourthArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("14000000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();
        var fifthArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("16000000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();
        stds.add(StopWatch.calculateStandardDeviation(firstArray));
        stds.add(StopWatch.calculateStandardDeviation(secondArray));
        stds.add(StopWatch.calculateStandardDeviation(thirdArray));
        stds.add(StopWatch.calculateStandardDeviation(fourthArray));
        stds.add(StopWatch.calculateStandardDeviation(fifthArray));

        System.out.println(stats1);
        System.out.println(stats2);
        System.out.println(stats3);
        System.out.println(stats4);
        System.out.println(stats5);
    }

    public static void measureSecond(Map<String, Long> timestamps, SkipList<Integer> list, List<Double> stds) {
        Random random = new Random();
        list.setP(1 / 2.0f);
        exploreMethodFp(timestamps, list);
        var stats1 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("10000 "))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();
        var stats2 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("100000 "))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();
        var stats3 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("1000000 "))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();

        var firstArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("10000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();
        var secondArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("100000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();
        var thirdArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("1000000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();

        System.out.println(stats1);
        System.out.println(stats2);
        System.out.println(stats3);

        stds.add(StopWatch.calculateStandardDeviation(firstArray));
        stds.add(StopWatch.calculateStandardDeviation(secondArray));
        stds.add(StopWatch.calculateStandardDeviation(thirdArray));

        stds.forEach(s -> System.out.println("std: " + s));
        stds.clear();

        //timestamps.forEach((key, value) -> System.out.println(key + ":" + value));
        System.out.println("------------------------");
        System.out.println("Start 1/4");
        list.setP(1 / 4.0f);
        exploreMethodFp(timestamps, list);
        stats1 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("10000 "))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();
        stats2 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("100000 "))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();
        stats3 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("1000000 "))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();

        firstArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("10000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();
        secondArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("100000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();
        thirdArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("1000000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();

        System.out.println(stats1);
        System.out.println(stats2);
        System.out.println(stats3);

        stds.add(StopWatch.calculateStandardDeviation(firstArray));
        stds.add(StopWatch.calculateStandardDeviation(secondArray));
        stds.add(StopWatch.calculateStandardDeviation(thirdArray));

        stds.forEach(s -> System.out.println("std: " + s));
        stds.clear();
        System.out.println("------------------------");
        System.out.println("Start 1/e");
        list.setP((float) (1 / Math.E));
        exploreMethodFp(timestamps, list);
        stats1 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("10000 "))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();
        stats2 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("100000 "))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();
        stats3 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("1000000 "))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();

        firstArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("10000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();
        secondArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("100000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();
        thirdArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("1000000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();

        System.out.println(stats1);
        System.out.println(stats2);
        System.out.println(stats3);

        stds.add(StopWatch.calculateStandardDeviation(firstArray));
        stds.add(StopWatch.calculateStandardDeviation(secondArray));
        stds.add(StopWatch.calculateStandardDeviation(thirdArray));

        stds.forEach(s -> System.out.println("std: " + s));
        stds.clear();

        System.out.println("------------------------");
        System.out.println("Start randomize");
        var rand = (Float) null;
        do {
            rand = random.nextFloat();
        }
        while (rand == 0);
        list.setP(rand);
        System.out.println("Random method generate: " + rand);
        exploreMethodFp(timestamps, list);
        stats1 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("10000 "))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();
        stats2 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("100000 "))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();
        stats3 = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("1000000 "))
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();

        firstArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("10000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();
        secondArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("100000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();
        thirdArray = timestamps.entrySet().stream()
                .filter(m -> m.getKey().contains("1000000"))
                .mapToLong(Map.Entry::getValue)
                .toArray();

        System.out.println(stats1);
        System.out.println(stats2);
        System.out.println(stats3);

        stds.add(StopWatch.calculateStandardDeviation(firstArray));
        stds.add(StopWatch.calculateStandardDeviation(secondArray));
        stds.add(StopWatch.calculateStandardDeviation(thirdArray));

        stds.forEach(s -> System.out.println("std: " + s));
    }

    public static void measureConfidenceInterval(Map<String, Long> timestamps, SkipList<Integer> list, List<Double> stds) {
        stds.clear();
        list.setP(1 / 2.0f);
        exploreConfidenceInterval(timestamps, list);
        var stats1 = timestamps.entrySet().stream()
                .skip(1)
                .limit(timestamps.size() - 2)
                .mapToInt((v) -> Math.toIntExact(v.getValue()))
                .summaryStatistics();

        var firstArray = timestamps.entrySet().stream()
                .mapToLong(Map.Entry::getValue)
                .toArray();

        System.out.println(stats1);

        stds.add(StopWatch.calculateStandardDeviation(firstArray));
        stds.forEach(s -> System.out.println("std: " + s));
    }

    public static void main(String[] args) {

        Map<String, Long> timestamps = new LinkedHashMap<>();
        SkipList<Integer> list = new SkipListImpl();
        List<Double> stds = new ArrayList<>();

        measureFirst(timestamps, list, stds);
        measureSecond(timestamps, list, stds);
        measureConfidenceInterval(timestamps, list, stds);
    }
}
