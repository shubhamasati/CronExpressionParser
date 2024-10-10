package org.hank.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum FieldType {

    MINUTE("minute",  0, 0, 59),
    HOUR("hour" , 1, 0, 23),
    DAY_OF_MONTH("dayOfMonth", 2, 1, 31),
    MONTH("month", 3,1, 12),
    DAY_OF_WEEK("dayOfWeek", 4,0, 6),
    COMMAND("command",5, Integer.MAX_VALUE, Integer.MIN_VALUE);

    private final String alias;
    private final int position;
    private final int minValue;
    private final int maxValue;

    FieldType(String alias, int position, int min, int max) {
        this.alias = alias;
        this.position = position;
        this.maxValue = max;
        this.minValue = min;
    }

    public static Map<Integer, FieldType> getPositionMap() {
        return Arrays.stream(values())
                .collect(Collectors.toMap(FieldType::getPosition, Function.identity()));
    }
}
