package com.khaledmosharraf.twtms.utils;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CommonMethod {
    public static List<Integer> getLastFewYears() {
        int currentYear = Year.now().getValue();
        return IntStream.rangeClosed(currentYear - 20, currentYear)
                .map(i -> currentYear - (i - (currentYear - 20))) // Reverse the order
                .boxed()
                .collect(Collectors.toList());
    }
}
