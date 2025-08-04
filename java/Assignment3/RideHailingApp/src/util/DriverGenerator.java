package util;

import java.util.ArrayList;
import java.util.List;

public class DriverGenerator {
    public static List<String> generateDriverNames(int count) {
        List<String> drivers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            drivers.add("Driver " + (char) ('A' + i));
        }
        return drivers;
    }
}
