package ru.job4j.todo.utilities;

import java.util.*;
import java.util.stream.Collectors;

public class TimeZoneUtil {

    private TimeZoneUtil() { }

    public static Map<String, String> getTimezonePairs() {
        return getAvailableTimezones().stream()
                .sorted(Comparator.comparing(TimeZone::getDisplayName))
                .collect(Collectors.toMap(
                        TimeZone::getID,
                        TimeZone::getDisplayName,
                        (s1, s2) -> s1,
                        LinkedHashMap::new));
    }

    private static List<TimeZone> getAvailableTimezones() {
        List<TimeZone> timezones = new ArrayList<>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            timezones.add(TimeZone.getTimeZone(timeId));
        }
        return timezones;
    }

}