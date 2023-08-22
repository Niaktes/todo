package ru.job4j.todo.utilities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import ru.job4j.todo.model.User;

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

    public static LocalDateTime changeToUsersTimezone(LocalDateTime original, User toUser) {
        String userTimezone = toUser.getTimezone() != null
                ? toUser.getTimezone() : TimeZone.getDefault().getID();
        return original.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of(userTimezone))
                .toLocalDateTime();
    }

    public static LocalDateTime changeToServersTimezone(LocalDateTime original, User fromUser) {
        String userTimezone = fromUser.getTimezone() != null
                ? fromUser.getTimezone() : TimeZone.getDefault().getID();
        return original.atZone(ZoneId.of(userTimezone))
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private static List<TimeZone> getAvailableTimezones() {
        List<TimeZone> timezones = new ArrayList<>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            timezones.add(TimeZone.getTimeZone(timeId));
        }
        return timezones;
    }

}