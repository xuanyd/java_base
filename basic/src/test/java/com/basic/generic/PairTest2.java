package com.basic.generic;

import java.time.LocalDate;

import org.junit.Test;

public class PairTest2 {
    @Test
    public void pairTest2() {
        LocalDate[] birthdays = {
            LocalDate.of(1962, 12, 9),
            LocalDate.of(1815, 6, 5),
            LocalDate.of(2008, 1, 1)
        };
        Pair<LocalDate> mm = ArrayAlg2.minmax(birthdays);
        System.out.println(mm.getFirst());
        System.out.println(mm.getSecond());
    }
}

class ArrayAlg2 {
    public static <T extends Comparable> Pair<T> minmax(T[] a) {
        if(a == null || a.length == 0)
            return null;
        T min = a[0];
        T max = a[0];
        for (int i = 1; i < a.length; i++ ) {
            if(min.compareTo(a[i]) > 0)
                min = a[i];
            if(max.compareTo(a[i]) < 0)
                max = a[i];
        }
        return new Pair<>(min, max);
    }
}